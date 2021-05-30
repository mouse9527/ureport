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
package com.bstek.ureport.chart;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.axes.impl.XAxes;
import com.bstek.ureport.chart.axes.impl.YAxes;
import com.bstek.ureport.chart.dataset.Dataset;
import com.bstek.ureport.chart.dataset.impl.BubbleDataset;
import com.bstek.ureport.chart.dataset.impl.ScatterDataset;
import com.bstek.ureport.chart.dataset.impl.category.BarDataset;
import com.bstek.ureport.chart.dataset.impl.category.LineDataset;
import com.bstek.ureport.chart.option.Option;
import com.bstek.ureport.chart.plugins.Plugin;
import com.bstek.ureport.model.Cell;

import java.util.ArrayList;
import java.util.List;

import static com.bstek.ureport.chart.plugins.Plugin.toJson;

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class Chart {
    public static final String EMPTY_PLUGINS_JSON = "\"datalabels\":{\"display\":false}";
    public static final String DEFAULT_Y_AXES_JSON = ",\"yAxes\":[{\"ticks\":{\"min\":0}}]";
    public static final String DEFAULT_SCALES_JSON = "\"yAxes\":[]";
    private Dataset dataset;
    private XAxes xaxes;
    private YAxes yaxes;
    private List<Option> options = new ArrayList<>();
    private List<Plugin> plugins = new ArrayList<>();

    public ChartData doCompute(Cell cell, Context context) {
        ChartData chartData = new ChartData(toJsonString(cell, context), cell);
        context.addChartData(chartData);
        return chartData;
    }

    private String toJsonString(Cell cell, Context context) {
        return "{" +
                "\"type\":\"" + getDataSetTypeJson() + "\"," +
                "\"data\":" + getDatasetJson(cell, context) + "," +
                "\"options\":" + getOptionsJson() +
                "}";
    }

    private String getDataSetTypeJson() {
        return dataset.getType();
    }

    private String getDatasetJson(Cell cell, Context context) {
        return dataset.buildDataJson(context, cell);
    }

    private String getOptionsJson() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        String optionsJson = appendOptionsJson();
        builder.append(optionsJson);
        builder.append(appendPluginsJson());
        builder.append(appendScales());
        builder.append("}");
        return builder.toString();
    }

    private String appendOptionsJson() {
        StringBuilder builder = new StringBuilder();
        if (hasOptions()) {
            for (int i = 0; i < options.size(); i++) {
                Option option = options.get(i);
                if (i > 0) {
                    builder.append(",");
                }
                builder.append(option.buildOptionJson());
            }
        }
        return builder.toString();
    }

    private boolean hasOptions() {
        return options != null && !options.isEmpty();
    }

    private String appendPluginsJson() {
        StringBuilder builder = new StringBuilder();
        if (hasOptions()) {
            builder.append(",");
        }
        builder.append("\"plugins\": {");

        if (hasPlugin()) {
            builder.append(toJson(plugins, dataset.getType()));
        } else {
            builder.append(EMPTY_PLUGINS_JSON);
        }
        builder.append("}");
        return builder.toString();
    }

    private boolean hasPlugin() {
        return plugins != null && !plugins.isEmpty();
    }

    private String appendScales() {
        StringBuilder builder = new StringBuilder();
        if ((hasXAxes() || yaxes != null) || hasYAxes(dataset)) {
            builder.append(",\"scales\":{");
            builder.append(getScalesJson());
            builder.append("}");
        }
        return builder.toString();
    }

    private String getScalesJson() {
        if (hasXAxes() || yaxes != null) {
            return String.format("%s%s", getXAxesJson(), getYAxesJson());
        } else if (hasYAxes(dataset)) {
            return DEFAULT_SCALES_JSON;
        }
        return "";
    }

    private String getXAxesJson() {
        if (hasXAxes()) {
            return String.format("\"xAxes\":[%s]", xaxes.toJson());
        } else {
            return "";
        }
    }

    private String getYAxesJson() {
        if (yaxes != null) {
            return String.format("%s%s]", getYaxesPrefix(), yaxes.toJson());
        } else if (hasYAxes(dataset)) {
            return DEFAULT_Y_AXES_JSON;
        }
        return "";
    }

    private String getYaxesPrefix() {
        if (hasXAxes()) {
            return ",\"yAxes\":[";
        } else {
            return "\"yAxes\":[";
        }
    }

    private boolean hasXAxes() {
        return xaxes != null;
    }

    // TODO: rename it
    private boolean hasYAxes(Dataset dataset) {
        if (dataset instanceof BarDataset) {
            return true;
        }
        if (dataset instanceof LineDataset) {
            return true;
        }
        if (dataset instanceof BubbleDataset) {
            return true;
        }
        if (dataset instanceof ScatterDataset) {
            return true;
        }
        return false;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public XAxes getXaxes() {
        return xaxes;
    }

    public void setXaxes(XAxes xaxes) {
        this.xaxes = xaxes;
    }

    public YAxes getYaxes() {
        return yaxes;
    }

    public void setYaxes(YAxes yaxes) {
        this.yaxes = yaxes;
    }
}
