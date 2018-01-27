package com.graduate.tool;

import com.graduate.entity.Matrix;

public class LLEEmbed {
    /**
     * 求与给定列向量相邻的列向量
     * @param matrices
     * @param i 该列向量在列向量数组中的下标
     * @return
     */
    private Matrix[] neighborhood( Matrix[] matrices, int i){
        Matrix[] answer;
        if (i>0&&i<matrices.length-1)
        {
            answer = new Matrix[2];
            answer[0] = matrices[i-1];
            answer[1] = matrices[i+1];
            return answer;
        }
        else if (i == 0&&i<matrices.length-1)
        {
            answer = new Matrix[1];
            answer[0] = matrices[i+1];
            return answer;
        }
        else if (i > 0&&i==matrices.length-1)
        {
            answer = new Matrix[1];
            answer[0] = matrices[i-1];
            return answer;
        }
        return null;
    }

    /**
     *
     */
    public void getZ(){

    }
}
