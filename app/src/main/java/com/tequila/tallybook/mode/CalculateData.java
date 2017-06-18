package com.tequila.tallybook.mode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tequila on 2017/6/13.
 */

public class CalculateData {

    private List<TallyViewHeadModel> HeadData;
    private List<TallyViewBodyModel> BodyData;

    public List<TallyViewHeadModel> getHeadData() {
        return HeadData;
    }

    public void setHeadData(List<TallyViewHeadModel> headData) {
        HeadData = headData;
    }

    public List<TallyViewBodyModel> getBodyData() {
        return BodyData;
    }

    public void setBodyData(List<TallyViewBodyModel> bodyData) {
        BodyData = bodyData;
    }

    private float SumData() {
        float sumData = 0.0000f;

        for (int i = 0; i < HeadData.size(); i++) {
            sumData += HeadData.get(i).getSumPrice();
        }

        return sumData;
    }

    public String showData () {
        return "总金额为 : " + SumData() + "元\n\n" + Calculate();
    }

    private String Calculate() {
        StringBuffer buffer = new StringBuffer();
        List<TallyViewHeadModel> tmpData = new ArrayList<>();

        for (int i = 0; i < HeadData.size(); i++) {
            for (int j = 0; j < BodyData.size(); j++) {
                if (HeadData.get(i).getMarkerName().equals(BodyData.get(j).getMarkerName())) {

                    BigDecimal bigDecimal1 = new BigDecimal(Float.toString(BodyData.get(j).getSumPrice()));
                    BigDecimal bigDecimal2 = new BigDecimal(Float.toString(HeadData.get(i).getSumPrice()));
                    float cha = bigDecimal1.subtract(bigDecimal2).floatValue();

                    TallyViewHeadModel model = new TallyViewHeadModel();
                    model.setMarkerName(HeadData.get(i).getMarkerName());
                    model.setSumPrice(cha);
                    tmpData.add(model);
                }
            }
        }

        return XunHuan(buffer, tmpData);
    }

    private String XunHuan (StringBuffer buffer, List<TallyViewHeadModel> tmpData) {

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

                            XunHuan(buffer, tmpData);
                            break;
                        }
                    }
                }
            }
        }

        return buffer.toString();
    }
}
