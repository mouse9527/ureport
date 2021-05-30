package com.bstek.ureport.chart;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.chart.axes.impl.XAxes;
import com.bstek.ureport.chart.axes.impl.YAxes;
import com.bstek.ureport.chart.dataset.Dataset;
import com.bstek.ureport.chart.option.impl.TitleOption;
import com.bstek.ureport.chart.plugins.DataLabelsPlugin;
import com.bstek.ureport.model.Cell;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class ChartTest {
    public static final String MOCK_CELL_NAME = "mock-cell-name";
    public static final String EXPECTED_JSON = "{\"type\":\"null\",\"data\":mock-dataset-output,\"options\":{\"title\":{\"display\":false,\"text\":\"null\",\"position\":\"top\",\"fontSize\":14,\"fontColor\":\"#666\",\"fontStyle\":\"bold\",\"padding\":\"10\"},\"plugins\": {\"datalabels\":{\"display\":false,\"font\":{\"size\":14,\"weight\":\"bold\"}}},\"scales\":{\"xAxes\":[{\"ticks\":{\"minRotation\":0}}],\"yAxes\":[{\"ticks\":{\"minRotation\":0}}]}}}";
    public static final String EXPECTED_JSON_WHEN_EMPTY_OPTIONS_AND_PLUGINS = "{\"type\":\"null\",\"data\":mock-dataset-output,\"options\":{\"plugins\": {\"datalabels\":{\"display\":false}},\"scales\":{\"xAxes\":[{\"ticks\":{\"minRotation\":0}}],\"yAxes\":[{\"ticks\":{\"minRotation\":0}}]}}}";

    @Test
    void should_do_compute() {
        Cell cell = new Cell();
        cell.setName(MOCK_CELL_NAME);
        Chart chart = new Chart();
        Dataset dataset = mock(Dataset.class);
        Context context = mock(Context.class);
        given(dataset.buildDataJson(context, cell)).willReturn("mock-dataset-output");
        chart.setDataset(dataset);
        chart.setXaxes(new XAxes());
        chart.setYaxes(new YAxes());
        chart.getOptions().add(new TitleOption());
        chart.getPlugins().add(new DataLabelsPlugin());

        ChartData chartData = chart.doCompute(cell, context);

        then(context).should().addChartData(chartData);
        assertThat(chartData.getJson()).isEqualTo(EXPECTED_JSON);
        assertThat(chartData.getId()).isEqualTo(MOCK_CELL_NAME);
    }

    @Test
    void should_do_compute_with_empty_options_and_plugins() {
        Cell cell = new Cell();
        cell.setName(MOCK_CELL_NAME);
        Chart chart = new Chart();
        Dataset dataset = mock(Dataset.class);
        Context context = mock(Context.class);
        given(dataset.buildDataJson(context, cell)).willReturn("mock-dataset-output");
        chart.setDataset(dataset);
        chart.setXaxes(new XAxes());
        chart.setYaxes(new YAxes());

        ChartData chartData = chart.doCompute(cell, context);

        assertThat(chartData.getJson()).isEqualTo(EXPECTED_JSON_WHEN_EMPTY_OPTIONS_AND_PLUGINS);
        assertThat(chartData.getId()).isEqualTo(MOCK_CELL_NAME);
    }
}
