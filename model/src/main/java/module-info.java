/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

module whichlicense.model {
    requires whichlicense.configuration;
    exports com.whichlicense.metadata.model;
    exports com.whichlicense.metadata.model.algebra;
    exports com.whichlicense.metadata.model.exporter;
    exports com.whichlicense.metadata.model.extractor;
    exports com.whichlicense.metadata.model.storage;
    exports com.whichlicense.metadata.model.traversal;
}
