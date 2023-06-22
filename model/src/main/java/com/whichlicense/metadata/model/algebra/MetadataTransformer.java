/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.model.algebra;

import com.whichlicense.metadata.model.Metadata;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class MetadataTransformer<IA extends I, I extends Metadata, T extends MetadataTransformation<I, O>, OA extends O, O extends Metadata> {
    private final Type inboundTypes, argsType, transformationType, outboundTypes, returnType;
    private final MethodHandle transformationMethod;

    public MetadataTransformer() throws NoSuchMethodException, IllegalAccessException {
        var superclass = getClass().getGenericSuperclass();
        inboundTypes = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        argsType = ((ParameterizedType) superclass).getActualTypeArguments()[1];
        transformationType = ((ParameterizedType) superclass).getActualTypeArguments()[2];
        outboundTypes = ((ParameterizedType) superclass).getActualTypeArguments()[3];
        returnType = ((ParameterizedType) superclass).getActualTypeArguments()[4];

        var paramTypes = Stream.of(((Class<?>) argsType).getDeclaredMethods())
                .filter(m -> Objects.equals(m.getName(), "licenseText"))
                .limit(1).map(Method::getParameterTypes)
                .flatMap(Stream::of).toList();

        var methodType = MethodType.methodType(Void.TYPE, paramTypes);
        transformationMethod = MethodHandles.lookup().findSpecial((Class<?>) transformationType,
                "licenseText", methodType, (Class<?>) transformationType);
    }

    private Class<?>[] algebraInterfaces() {
        return Stream.concat(Stream.of(transformationType), Stream.of(inboundTypes, outboundTypes).mapMulti((type, consumer) -> {
            if (type instanceof Class<?> interfaceType) {
                for (var nestedInterfaceType : interfaceType.getInterfaces()) {
                    if (Metadata.class.isAssignableFrom(nestedInterfaceType)) {
                        consumer.accept(nestedInterfaceType);
                    }
                }
            }
        })).toArray(Class<?>[]::new);
    }

    @SuppressWarnings("unchecked")
    public IA into(OA sink) {
        return (IA) Proxy.newProxyInstance(MetadataTransformer.class.getClassLoader(), algebraInterfaces(),
                (proxy, method, args) -> {
                    if (Objects.equals(method.getDeclaringClass(), transformationType)) {
                        return transformationMethod.bindTo(proxy).invokeWithArguments(args);
                    } else {
                        return method.invoke(sink, args);
                    }
                });
    }

    @Override
    public String toString() {
        return "MetadataTransformer{" +
                "inboundTypes=" + inboundTypes +
                ", argsType=" + argsType +
                ", transformationType=" + transformationType +
                ", outboundTypes=" + outboundTypes +
                ", returnType=" + returnType +
                '}';
    }
}
