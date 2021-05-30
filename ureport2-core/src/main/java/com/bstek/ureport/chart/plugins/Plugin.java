/*******************************************************************************
 * Copyright 2017 Bstek
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.bstek.ureport.chart.plugins;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jacky.gao
 * @since 2018年7月6日
 */
public interface Plugin {
    static String toJson(List<Plugin> plugins, String datasetType) {
        return plugins.stream()
                .map(plugin -> plugin.toJson(datasetType))
                .collect(Collectors.joining());
    }

    String toJson(String type);

    String getName();
}
