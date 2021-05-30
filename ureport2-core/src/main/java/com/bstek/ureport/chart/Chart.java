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

/**
 * @author Jacky.gao
 * @since 2017年6月8日
 */
public class Chart {
    private Dataset dataset;
    private XAxes xaxes;
    private YAxes yaxes;
    private List<Option> options = new ArrayList<>();
    private List<Plugin> plugins = new ArrayList<>();

    public ChartData doCompute(Cell cell, Context context) {
        ChartData chartData = new ChartData(toString(cell, context), cell);
        context.addChartData(chartData);
        return chartData;
    }

    private String toString(Cell cell, Context context) {
        return "{" +
                "\"type\":\"" + dataset.getType() + "\"," +
                "\"data\":" + dataset.buildDataJson(context, cell) + "," +
                "\"options\":{" +
                parseOptions() +
                "}" +
                "}";
    }

    private StringBuilder parseOptions() {
        boolean withoption = false;
        StringBuilder sb1 = new StringBuilder();
        if (options != null && !options.isEmpty()) {
            withoption = true;
            sb1.append(parseOptionsJson());
        }
        sb1.append(parsePluginsJson(withoption));
        sb1.append(parseAxes());
        return sb1;
    }

    private StringBuilder parseAxes() {
        StringBuilder sb1 = new StringBuilder();
        if (xaxes != null || yaxes != null) {
            sb1.append(",");
            sb1.append("\"scales\":{");
            if (xaxes != null) {
                sb1.append("\"xAxes\":[");
                sb1.append(xaxes.toJson());
                sb1.append("]");
            }
            if (yaxes != null) {
                if (xaxes != null) {
                    sb1.append(",\"yAxes\":[");
                } else {
                    sb1.append("\"yAxes\":[");
                }
                sb1.append(yaxes.toJson());
                sb1.append("]");
            } else {
                if (hasYAxes(dataset)) {
                    sb1.append(",\"yAxes\":[{\"ticks\":{\"min\":0}}]");
                }
            }
            sb1.append("}");
        } else {
            if (hasYAxes(dataset)) {
                sb1.append(",");
                sb1.append("\"scales\":{\"yAxes\":[]}");
            }
        }
        return sb1;
    }

    private StringBuilder parsePluginsJson(boolean withoption) {
        StringBuilder sb = new StringBuilder();
        if (plugins != null && !plugins.isEmpty()) {
            if (withoption) {
                sb.append(",");
            }
            sb.append("\"plugins\": {");
            for (Plugin plugin : plugins) {
                String pluginJson = plugin.toJson(dataset.getType());
                if (pluginJson != null) {
                    sb.append(pluginJson);
                }
            }
            sb.append("}");
        } else {
            sb.append("\"plugins\": {");
            sb.append("\"datalabels\":{\"display\":false}");
            sb.append("}");
        }
        return sb;
    }

    private StringBuilder parseOptionsJson() {
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            Option option = options.get(i);
            if (i > 0) {
                sb1.append(",");
            }
            sb1.append(option.buildOptionJson());
        }
        return sb1;
    }

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
