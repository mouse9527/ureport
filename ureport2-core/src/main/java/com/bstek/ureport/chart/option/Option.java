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
package com.bstek.ureport.chart.option;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public interface Option {

    // TODO: rename it
    static String getOptionsJson(List<Option> options) {
        return Optional.ofNullable(options).orElse(Collections.emptyList()).stream()
                .map(Option::buildOptionJson)
                .collect(Collectors.joining(","));
    }

    static boolean hasOptions(List<Option> options) {
        return options != null && !options.isEmpty();
    }

    String buildOptionJson();

    String getType();
}
