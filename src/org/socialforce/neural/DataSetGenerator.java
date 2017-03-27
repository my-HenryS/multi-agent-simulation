package org.socialforce.neural;

/**
 * Created by sunjh1999 on 2017/3/27.
 */
public interface DataSetGenerator {
    /**
     * 指定读取的文件
     * @param directory
     */
    void readFile(String directory);

    /**
     * 指定输出结果的文件
     * @param directory
     */
    void toFile(String directory);

    /**
     * 按矩阵输出
     */
    double[][] toMatrix();
}
