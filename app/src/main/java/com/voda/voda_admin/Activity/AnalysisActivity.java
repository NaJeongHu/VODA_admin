package com.voda.voda_admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.voda.voda_admin.R;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {

    private PieChart chart1;
    private BarChart chart2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        init();

        setPieChart();
        setBarChart();

    }


    public void init(){

        chart1 = findViewById(R.id.tab1_chart_1);
        chart2 = findViewById(R.id.tab1_chart_2);

    }

    // 파이 차트 설정
    private void setPieChart() {

        ArrayList a = new ArrayList();
        a.add((new PieEntry(10,"제육김밥")));
        a.add((new PieEntry(15,"초코김밥")));
        a.add((new PieEntry(35,"딸기김밥")));
        a.add((new PieEntry(40,"누드김밥")));

        chart1.animateXY(1000,1000);
        chart1.setDescription(null);

        PieDataSet dataSet = new PieDataSet(a,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        int[] colors = {Color.parseColor("#ee8155"),Color.parseColor("#f5c172"),Color.parseColor("#89d5c9"),Color.parseColor("#adc965")};
        dataSet.setColors(colors);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.WHITE);

        Legend legend = chart1.getLegend();
        legend.setTextSize(15f);
        legend.setFormSize(15f);
        chart1.setData(data);

    }

    // 막대 차트 설정
    private void setBarChart() {

        ArrayList a = new ArrayList();
        a.add((new BarEntry(0,70f)));
        a.add((new BarEntry(1,62f)));
        a.add((new BarEntry(2,34f)));
        a.add((new BarEntry(3,102f)));
        a.add((new BarEntry(4,79f)));
        a.add((new BarEntry(5,121f)));
        a.add((new BarEntry(6,52f)));
        a.add((new BarEntry(7,62f)));

        ArrayList<String> xLabel = new ArrayList<>();
        for(int i=10;i<18;i++){
            xLabel.add(""+i);
        }

        chart2.animateXY(1000,1000);
        chart2.setDescription(null);

        XAxis xaxis = chart2.getXAxis();
        xaxis.setDrawGridLines(false);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setTextSize(15);
        xaxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xLabel.get((int)value)+"일";
            }
        });

        BarDataSet dataSet = new BarDataSet(a,"매출액(만원)");

        dataSet.setColor(Color.parseColor("#f5c172"));

        BarData data = new BarData((dataSet));
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);

        Legend legend = chart2.getLegend();
        legend.setTextSize(15f);
        legend.setFormSize(15f);
        chart2.setData(data);
    }


}