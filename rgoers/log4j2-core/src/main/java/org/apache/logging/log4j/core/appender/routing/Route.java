/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.logging.log4j.core.appender.routing;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttr;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.PluginNode;
import org.apache.logging.log4j.status.StatusLogger;

import java.util.List;

/**
 *
 */
@Plugin(name="Route", type="Core", printObject=true, deferChildren=true)
public class Route {

    private final Node node;
    private final String appenderRef;
    private final String key;
    private static final Logger logger = StatusLogger.getLogger();

    private Route(Node node, String appenderRef, String key) {
        this.node = node;
        this.appenderRef = appenderRef;
        this.key = key;
    }

    public Node getNode() {
        return node;
    }

    public String getAppenderRef() {
        return appenderRef;
    }

    public String getKey() {
        return key;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Route(");
        sb.append("type=");
        if (appenderRef != null) {
            sb.append("static Reference=").append(appenderRef);
        } else {
            sb.append("dynamic");
        }
        if (key != null) {
            sb.append(" key='").append(key).append("'");
        } else {
            sb.append(" default");
        }
        sb.append(")");
        return sb.toString();
    }

    @PluginFactory
    public static Route createRoute(@PluginAttr("appender-ref") String appenderRef,
                                    @PluginAttr("key") String key,
                                    @PluginNode Node node) {
        if (node != null && node.hasChildren()) {
            for (Node child : node.getChildren()) {

            }
            if (appenderRef != null) {
                logger.error("A route cannot be configured with an appender reference and an appender definition");
                return null;
            }
        } else {
            if (appenderRef == null) {
                logger.error("A route must specify an appender reference or an appender definition");
                return null;
            }
        }
        return new Route(node, appenderRef, key);
    }
}