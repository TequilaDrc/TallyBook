package com.tequila.tallybook.mode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tequila on 2017/6/13.
 */

public class CalculateData {

    private List<TallyViewHeadModel> data;

    public List<TallyViewHeadModel> getData() {
        return data;
    }

    public void setData(List<TallyViewHeadModel> data) {
        this.data = data;
    }

    private float AverageData() {
        return SumData() / data.size();
    }

    private float SumData() {
        float sumData = 0.0000f;

        for (int i = 0; i < data.size(); i++) {
            sumData += data.get(i).getSumPrice();
        }

        return sumData;
    }

    public String showData () {
        return "总金额为 : " + SumData() + "元\n" + "平均每个人消费金额 : " + AverageData() + "元\n\n" + Calculate();
    }

    private String Calculate () {
        StringBuffer buffer = new StringBuffer();
        List<TallyViewHeadModel> tmpData = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            TallyViewHeadModel model = new TallyViewHeadModel();
            model.setSumPrice(AverageData() - data.get(i).getSumPrice());
            model.setMarkerName(data.get(i).getMarkerName());

            tmpData.add(model);
        }

        for (int i = 0; i < tmpData.size(); i++) {
            if (tmpData.get(i).getSumPrice() > 0) {
                for (int j = 0; j < tmpData.size(); j++) {
                    if (tmpData.get(j).getSumPrice() < 0) {
                        BigDecimal bigDecimal1 = new BigDecimal(Float.toString(tmpData.get(i).getSumPrice()));
                        BigDecimal bigDecimal2 = new BigDecimal(Float.toString(tmpData.get(j).getSumPrice()));
                        float v = bigDecimal1.add(bigDecimal2).floatValue();

                        if (v < 0) {
                            buffer.append(tmpData.get(i).getMarkerName() + "需给" + tmpData.get(j).getMarkerName() + " : " + tmpData.get(i).getSumPrice() + "元\n");
                            tmpData.get(i).setSumPrice(0);
                            tmpData.get(j).setSumPrice(v);
                            break;
                        } else if (v == 0) {
                            buffer.append(tmpData.get(i).getMarkerName() + "需给" + tmpData.get(j).getMarkerName() + " : " + tmpData.get(i).getSumPrice() + "元\n");
                            tmpData.get(i).setSumPrice(0);
                            tmpData.get(j).setSumPrice(0);
                            break;
                        } else if (v > 0) {

                            buffer.append(tmpData.get(i).getMarkerName() + "需给" + tmpData.get(j).getMarkerName() + " : " + Math.abs(tmpData.get(j).getSumPrice()) + "元\n");
                            tmpData.get(i).setSumPrice(v);
                            tmpData.get(j).setSumPrice(0);

                            for (int k = 0; k < tmpData.size(); k++) {

                                if (tmpData.get(k).getSumPrice() < 0) {
                                    BigDecimal b1 = new BigDecimal(Float.toString(tmpData.get(i).getSumPrice()));
                                    BigDecimal b2 = new BigDecimal(Float.toString(tmpData.get(k).getSumPrice()));
                                    float va = b1.add(b2).floatValue();

                                    if (va < 0) {
                                        buffer.append(tmpData.get(i).getMarkerName() + "需给" + tmpData.get(k).getMarkerName() + " : " + tmpData.get(i).getSumPrice() + "元\n");
                                        tmpData.get(i).setSumPrice(0);
                                        tmpData.get(k).setSumPrice(va);
                                        break;
                                    } else if (va == 0) {
                                        buffer.append(tmpData.get(i).getMarkerName() + "需给" + tmpData.get(k).getMarkerName() + " : " + tmpData.get(i).getSumPrice() + "元\n");
                                        tmpData.get(i).setSumPrice(0);
                                        tmpData.get(j).setSumPrice(0);
                                        break;
                                    } else if (va > 0) {
                                        buffer.append(tmpData.get(i).getMarkerName() + "需给" + tmpData.get(k).getMarkerName() + " : " + Math.abs(tmpData.get(j).getSumPrice()) + "元\n");
                                        tmpData.get(i).setSumPrice(va);
                                        tmpData.get(j).setSumPrice(0);
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

        return buffer.toString();
    }
}
