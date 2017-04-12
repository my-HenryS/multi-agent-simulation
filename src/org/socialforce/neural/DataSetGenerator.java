package org.socialforce.neural;

/**
 * Created by sunjh1999 on 2017/3/27.
 */
public interface DataSetGenerator {
    /**
     * 指定读取的文件
     * @param directory
     */
    void readFile(String directory, int timeInterval);

    /**
     * 指定输出结果的文件
     * @param directory
     */
    void toFile(String directory);

    /**
     * 以一定频率指定输出结果的文件
     * @param directory
     */
    void toFile(String directory, int interval);

    /**
     * 按矩阵输出
     */
    double[][] toMatrix();

    /**
     * 增加输出
     * @param output 输出值
     * @param times 重复采样次数
     */
    void addOutput(double[] output, int times);
}
