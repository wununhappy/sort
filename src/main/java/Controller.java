import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class Controller extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SortingAlgorithmsGUI.fxml")));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }



    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private TextField inputTextField;

    @FXML
    private ComboBox<String> algorithmComboBox;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private ComboBox<String> algorithmComboBox1;

    @FXML
    private ComboBox<String> algorithmComboBox2;

    @FXML
    private TextArea outputTextArea2;

    @FXML
    private PieChart comparisonPieChart;




    @FXML
    public void initialize() {
        // 在初始化时，将 pane1 显示，将 pane2 隐藏
        pane1.setVisible(true);
        pane2.setVisible(false);
    }

    @FXML
    private void handleSortButton() {
        // 设置右侧Pane为id为1的页面
        pane1.setVisible(true);
        pane2.setVisible(false);
    }

    @FXML
    private void handleCompareButton() {
        // 设置右侧Pane为id为2的页面
        pane2.setVisible(true);
        pane1.setVisible(false);

    }

    @FXML
    public void handleStartButton() {
        mergeSortCount = 1;

        // 清除以前的输出
        outputTextArea.clear();

        // 获取输入框中的数字字符串，并转换成整数数组
        String inputText = inputTextField.getText();

        // 输入检查：检查是否输入了待排序数据
        if (inputText.isEmpty()) {
            showPopup("请输入待排序数据");
            return; // 结束方法
        }

        String[] numbersAsString = inputText.split("\\s+"); // 假设数字之间用空格分隔

        // 输入检查：检查是否输入的数据都是数字
        for (String num : numbersAsString) {
            if (!num.matches("-?\\d+")) { // 使用正则表达式检查是否为整数（允许负数）
                showPopup("请输入数字数据");
                return; // 结束方法
            }
        }

        int[] numbers = new int[numbersAsString.length];

        for (int i = 0; i < numbersAsString.length; i++) {
            try {
                numbers[i] = Integer.parseInt(numbersAsString[i]);
            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
        }

        // 获取选择的排序算法
        String selectedAlgorithm = algorithmComboBox.getValue();

        // 检查是否成功获取到排序算法的值
        if (selectedAlgorithm == null || selectedAlgorithm.isEmpty()) {
            // 如果没有获取到值，弹出提示框
            showPopup("请选择排序算法");
            return; // 结束方法
        }

        // 根据选择的排序算法对数字数组进行排序，并将每一步结果输出到TextArea中
        switch (selectedAlgorithm) {
            case "直接插入排序":
                directInsertionSort(numbers);
                break;
            case "希尔排序":
                shellSort(numbers);
                break;
            case "冒泡排序":
                improvedBubbleSort(numbers);
                break;
            case "快速排序":
                quickSort(numbers, 0, numbers.length - 1);
                break;
            case "直接选择排序":
                directSelectionSort(numbers);
                break;
            case "堆排序":
                heapSort(numbers);
                break;
            case "归并排序":
                mergeSort(numbers, 0, numbers.length - 1);
                break;
            case "链式基数排序":
                radixSort(numbers);
                break;
            // 添加其他排序算法的case语句
        }

    }

    @FXML
    private void handleStartComparisonButton() {
        String algorithm1 = algorithmComboBox1.getValue();
        String algorithm2 = algorithmComboBox2.getValue();

        if (algorithm1 == null || algorithm2 == null)
            showPopup("请选择对比算法");


        int[] data = generateRandomData(30000);

        long startTime1 = System.nanoTime();
        sortArray(data, algorithm1);
        long endTime1 = System.nanoTime();
        long executionTime1 = endTime1 - startTime1;

        long startTime2 = System.nanoTime();
        sortArray(data, algorithm2);
        long endTime2 = System.nanoTime();
        long executionTime2 = endTime2 - startTime2;

        // 将结果输出到 TextArea 中
        String resultText = String.format("算法1：%s\n运行时间：%d 纳秒\n\n算法2：%s\n运行时间：%d 纳秒", algorithm1, executionTime1, algorithm2, executionTime2);
        outputTextArea2.setText(resultText);

        // 生成扇形图数据
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(algorithm1, executionTime1),
                new PieChart.Data(algorithm2, executionTime2)
        );

        // 设置扇形图数据
        comparisonPieChart.setData(pieChartData);

    }

    @FXML
    public void handleStartComparisonallButton() {
        int[] data = generateRandomData(30000);
        long startTime1 = System.nanoTime();
        sortArray(data, "直接插入排序");
        long endTime1 = System.nanoTime();
        long executionTime1 = endTime1 - startTime1;

        long startTime2 = System.nanoTime();
        sortArray(data, "希尔排序");
        long endTime2 = System.nanoTime();
        long executionTime2 = endTime2 - startTime2;

        long startTime3 = System.nanoTime();
        sortArray(data, "冒泡排序");
        long endTime3 = System.nanoTime();
        long executionTime3 = endTime3 - startTime3;

        long startTime4 = System.nanoTime();
        sortArray(data, "快速排序");
        long endTime4 = System.nanoTime();
        long executionTime4 = endTime4 - startTime4;

        long startTime5 = System.nanoTime();
        sortArray(data, "直接选择排序");
        long endTime5 = System.nanoTime();
        long executionTime5 = endTime5 - startTime5;

        long startTime6 = System.nanoTime();
        sortArray(data, "堆排序");
        long endTime6 = System.nanoTime();
        long executionTime6 = endTime6 - startTime6;

        long startTime7 = System.nanoTime();
        sortArray(data, "归并排序");
        long endTime7 = System.nanoTime();
        long executionTime7 = endTime7 - startTime7;

        long startTime8 = System.nanoTime();
        sortArray(data, "链式基数排序");
        long endTime8 = System.nanoTime();
        long executionTime8 = endTime8 - startTime8;
        // 将结果输出到 TextArea 中
        String resultText = String.format("算法1：%s\n运行时间：%d 纳秒\n\n算法2：%s\n运行时间：%d 纳秒\n\n算法3：%s\n运行时间：%d 纳秒\n\n算法4：%s\n运行时间：%d 纳秒\n\n算法5：%s\n运行时间：%d 纳秒\n\n算法6：%s\n运行时间：%d 纳秒\n\n算法7：%s\n运行时间：%d 纳秒\n\n算法8：%s\n运行时间：%d 纳秒", "直接插入排序", executionTime1, "希尔排序", executionTime2, "冒泡排序", executionTime3, "快速排序", executionTime4, "直接选择排序", executionTime5, "堆排序", executionTime6, "归并排序", executionTime7, "链式基数排序", executionTime8);
        outputTextArea2.setText(resultText);

        int[] sortedArray1 = sortArray1(Arrays.copyOf(data, data.length), "直接插入排序");
        int[] sortedArray2 = sortArray1(Arrays.copyOf(data, data.length), "希尔排序");
        int[] sortedArray3 = sortArray1(Arrays.copyOf(data, data.length), "冒泡排序");
        int[] sortedArray4 = sortArray1(Arrays.copyOf(data, data.length), "快速排序");
        int[] sortedArray5 = sortArray1(Arrays.copyOf(data, data.length), "直接选择排序");
        int[] sortedArray6 = sortArray1(Arrays.copyOf(data, data.length), "堆排序");
        int[] sortedArray7 = sortArray1(Arrays.copyOf(data, data.length), "归并排序");
        int[] sortedArray8 = sortArray1(Arrays.copyOf(data, data.length), "链式基数排序");

        writeResultToFile("直接插入排序.txt", sortedArray1);
        writeResultToFile("希尔排序.txt", sortedArray2);
        writeResultToFile("冒泡排序.txt", sortedArray3);
        writeResultToFile("快速排序.txt", sortedArray4);
        writeResultToFile("直接选择排序.txt", sortedArray5);
        writeResultToFile("堆排序.txt", sortedArray6);
        writeResultToFile("归并排序.txt", sortedArray7);
        writeResultToFile("链式基数排序.txt", sortedArray8);


        // 生成扇形图数据
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("直接插入排序", executionTime1),
                new PieChart.Data("希尔排序", executionTime2),
                new PieChart.Data("冒泡排序", executionTime3),
                new PieChart.Data("快速排序", executionTime4),
                new PieChart.Data("直接选择排序", executionTime5),
                new PieChart.Data("堆排序", executionTime6),
                new PieChart.Data("归并排序", executionTime7),
                new PieChart.Data("链式基数排序", executionTime8)
        );

        // 设置扇形图数据
        comparisonPieChart.setData(pieChartData);
    }




    // pane1的start
    private void directInsertionSort(int[] array) {
        int n=array.length;
        for(int i=1;i<n;i++) {
            int key = array[i];
            int j=i-1;
            while (j>=0&&array[j]>key) {
                array[j+1] = array[j];
                j--;
            }
            array[j+1]=key;


            // 输出每一步排序的结果到TextArea中
            outputTextArea.appendText("第 " + i + " 步排序结果: ");
            for (int num : array) {
                outputTextArea.appendText(num + " ");
            }
            outputTextArea.appendText("\n");
        }
        outputTextArea.appendText("最终排序结果: ");
        for (int num : array) {
            outputTextArea.appendText(num + " ");
        }
        outputTextArea.appendText("\n");
    }

    private void shellSort(int[] array) {
        int n = array.length;
        int gap = n / 2;

        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int temp = array[i];
                int j = i;

                // 对间隔为 gap 的元素进行插入排序
                while (j >= gap && array[j - gap] > temp) {
                    array[j] = array[j - gap];
                    j -= gap;
                }

                array[j] = temp;
            }

            // 缩小间隔
            gap /= 2;

            // 输出每一步排序的结果到TextArea中
            outputTextArea.appendText("间隔为 " + gap + " 的排序结果: ");
            for (int num : array) {
                outputTextArea.appendText(num + " ");
            }
            outputTextArea.appendText("\n");
        }

        outputTextArea.appendText("最终排序结果: ");
        for (int num : array) {
            outputTextArea.appendText(num + " ");
        }
        outputTextArea.appendText("\n");
    }

    private void improvedBubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // 交换元素
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;

                    swapped = true;
                }
            }

            // 如果没有元素交换，说明数组已经有序，可以提前结束循环
            if (!swapped) {
                break;
            }

            // 输出每一步排序的结果到TextArea中
            outputTextArea.appendText("第 " + (i + 1) + " 步排序结果: ");
            for (int num : array) {
                outputTextArea.appendText(num + " ");
            }
            outputTextArea.appendText("\n");
        }
        outputTextArea.appendText("最终排序结果: ");
        for (int num : array) {
            outputTextArea.appendText(num + " ");
        }
        outputTextArea.appendText("\n");
    }

    private int quickSortCount = 1; // 新增计数器，用于记录快速排序的次数

    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            // 在数组中选取一个基准元素，并将数组分为两部分
            int partitionIndex = partition(array, low, high);

            // 递归对基准元素左边的子数组进行快速排序
            quickSort(array, low, partitionIndex - 1);

            // 输出每一次排序的结果到TextArea中
            outputTextArea.appendText("第 " + quickSortCount + " 次快速排序结果: ");
            for (int num : array) {
                outputTextArea.appendText(num + " ");
            }
            outputTextArea.appendText("\n");

            quickSortCount++;

            // 递归对基准元素右边的子数组进行快速排序
            quickSort(array, partitionIndex + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;

                // 交换 array[i] 和 array[j]
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        // 交换 array[i+1] 和 array[high]，将基准元素放到正确的位置
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        // 返回基准元素的索引
        return i + 1;
    }

    private void directSelectionSort(int[] array) {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // 找到未排序部分的最小元素的索引
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }

            // 交换找到的最小元素和当前位置的元素
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;

            // 输出每一步排序的结果到TextArea中
            outputTextArea.appendText("第 " + (i + 1) + " 步排序结果: ");
            for (int num : array) {
                outputTextArea.appendText(num + " ");
            }
            outputTextArea.appendText("\n");
        }

        outputTextArea.appendText("最终排序结果: ");
        for (int num : array) {
            outputTextArea.appendText(num + " ");
        }
        outputTextArea.appendText("\n");
    }

    private void heapSort(int[] array) {
        int n = array.length;

        // 构建最大堆
        buildMaxHeap(array);

        // 从堆中提取元素，构建有序数组
        for (int i = n - 1; i > 0; i--) {
            // 交换堆顶元素和当前堆的最后一个元素
            swap(array, 0, i);

            // 调整堆，使其满足最大堆性质
            heapify(array, i, 0);

            // 输出每一步排序的结果到TextArea中
            outputTextArea.appendText("第 " + (n - i) + " 步排序结果: ");
            for (int num : array) {
                outputTextArea.appendText(num + " ");
            }
            outputTextArea.appendText("\n");
        }
        outputTextArea.appendText("最终排序结果: ");
        for (int num : array) {
            outputTextArea.appendText(num + " ");
        }
        outputTextArea.appendText("\n");
    }

    private void buildMaxHeap(int[] array) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
    }

    private void heapify(int[] array, int heapSize, int rootIndex) {
        int largest = rootIndex;
        int leftChild = 2 * rootIndex + 1;
        int rightChild = 2 * rootIndex + 2;

        // 找到左子节点和右子节点中的最大值
        if (leftChild < heapSize && array[leftChild] > array[largest]) {
            largest = leftChild;
        }
        if (rightChild < heapSize && array[rightChild] > array[largest]) {
            largest = rightChild;
        }

        // 如果最大值不是根节点，交换根节点和最大值，并递归调整堆
        if (largest != rootIndex) {
            swap(array, rootIndex, largest);
            heapify(array, heapSize, largest);
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private int mergeSortCount = 1; // 新增计数器，用于记录归并排序的次数

    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            // 找到数组中间的索引
            int middle = (left + right) / 2;

            // 递归地对数组的两部分进行归并排序
            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);

            // 合并两个有序数组
            merge(array, left, middle, right);

            // 输出每一次排序的结果到TextArea中
            outputTextArea.appendText("第 " + mergeSortCount + " 次归并排序结果: ");
            for (int num : array) {
                outputTextArea.appendText(num + " ");
            }
            outputTextArea.appendText("\n");

            mergeSortCount++;
        }
    }

    private void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // 创建临时数组
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // 将数据复制到临时数组 leftArray[] 和 rightArray[]
        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, middle + 1, rightArray, 0, n2);

        // 合并临时数组
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // 将 leftArray[] 的剩余元素复制到原数组
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        // 将 rightArray[] 的剩余元素复制到原数组
        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    private void radixSort(int[] array) {
        // 获取最大值，以确定最大位数
        int max = getMax(array);

        // 进行基数排序
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(array, exp);
        }
    }

    private int getMax(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    private void countingSort(int[] array, int exp) {
        int n = array.length;

        LinkedList<Integer>[] buckets = new LinkedList[19];

        //初始化每个桶
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }

        //将数组里面的数据放入桶，开始排序
        for (int i = 0; i < n; i++) {
            int index = (array[i] / exp) % 10 + 9;
            buckets[index].add(array[i]);
        }

        //数据出桶
        int index = 0;
        for (int i = 0; i < 19; i++) {
            for (int num : buckets[i]) {
                array[index++] = num;
            }
            buckets[i].clear();
        }

        outputTextArea.appendText("基数排序 (以 " + exp + "位计数): ");
        for (int num : array) {
            outputTextArea.appendText(num + " ");
        }
        outputTextArea.appendText("\n");
    }




    //pane2的start和startall
    private void sortArray(int[] array, String algorithm) {
        switch (algorithm) {
            case "直接插入排序":
                directInsertionSort1(array);
                break;
            case "希尔排序":
                shellSort1(array);
                break;
            case "冒泡排序":
                improvedBubbleSort1(array);
                break;
            case "快速排序":
                quickSort1(array, 0, array.length - 1);
                break;
            case "直接选择排序":
                directSelectionSort1(array);
                break;
            case "堆排序":
                heapSort1(array);
                break;
            case "归并排序":
                mergeSort1(array, 0, array.length - 1);
                break;
            case "链式基数排序":
                radixSort1(array);
                break;
            // 添加其他算法的情况...
            default:
                // 处理未知算法
                break;
        }
    }

    private int[] generateRandomData(int size) {
        Random random = new Random(); // 默认使用系统时间作为种子
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt(100000);
        }
        return data;
    }

    private void shellSort1(int[] array) {
        int n = array.length;
        int gap = n / 2;

        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int temp = array[i];
                int j = i;

                // 对间隔为 gap 的元素进行插入排序
                while (j >= gap && array[j - gap] > temp) {
                    array[j] = array[j - gap];
                    j -= gap;
                }

                array[j] = temp;
            }

            // 缩小间隔
            gap /= 2;
        }
    }

    private void improvedBubbleSort1(int[] array) {
        int n = array.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // 交换元素
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;

                    swapped = true;
                }
            }

            // 如果没有元素交换，说明数组已经有序，可以提前结束循环
            if (!swapped) {
                break;
            }
        }

    }

    private void quickSort1(int[] array, int low, int high) {
        if (low < high) {
            // 在数组中选取一个基准元素，并将数组分为两部分
            int partitionIndex = partition(array, low, high);

            // 递归对基准元素左边的子数组进行快速排序
            quickSort1(array, low, partitionIndex - 1);


            // 递归对基准元素右边的子数组进行快速排序
            quickSort1(array, partitionIndex + 1, high);
        }
    }

    private void directSelectionSort1(int[] array) {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // 找到未排序部分的最小元素的索引
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }

            // 交换找到的最小元素和当前位置的元素
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;


        }


    }

    private void heapSort1(int[] array) {
        int n = array.length;

        // 构建最大堆
        buildMaxHeap(array);

        // 从堆中提取元素，构建有序数组
        for (int i = n - 1; i > 0; i--) {
            // 交换堆顶元素和当前堆的最后一个元素
            swap(array, 0, i);

            // 调整堆，使其满足最大堆性质
            heapify(array, i, 0);


        }

    }

    private void mergeSort1(int[] array, int left, int right) {
        if (left < right) {
            // 找到数组中间的索引
            int middle = (left + right) / 2;

            // 递归地对数组的两部分进行归并排序
            mergeSort1(array, left, middle);
            mergeSort1(array, middle + 1, right);

            // 合并两个有序数组
            merge(array, left, middle, right);

        }
    }

    private void directInsertionSort1(int[] array) {
        int n = array.length;

        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;

            // 将大于 key 的元素都向右移动一个位置
           while (j>=0 && array[j]>key){
               array[j+1] = array[j];
               j++;
            }
            array[j + 1] = key;
        }
    }
    private void radixSort1(int[] array) {
        // 获取最大值，以确定最大位数
        int max = getMax1(array);

        // 进行基数排序
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort1(array, exp);
        }
    }

    // 获取数组中的最大值
    private int getMax1(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    // 基于计数排序的基数排序
    private void countingSort1(int[] array, int exp) {
        int n = array.length;

        LinkedList<Integer>[] buckets = new LinkedList[19];

        //初始化每个桶
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }

        //将数组里面的数据放入桶，开始排序
        for (int i = 0; i < n; i++) {
            int index = (array[i] / exp) % 10 +9;
            buckets[index].add(array[i]);
        }

        //数据出桶
        int index = 0;
        for (int i = 0; i < 19; i++) {
            for (int num : buckets[i]) {
                array[index++] = num;
            }
            buckets[i].clear();
        }
    }

    private void showPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText(message);
        // 设置字体大小
        DialogPane dialogPane = alert.getDialogPane();
        Font font = new Font(16); // 调整字体大小
        dialogPane.setStyle("-fx-font-size: " + font.getSize() + "px;");

        alert.showAndWait();
    }

    private int[] sortArray1(int[] array, String algorithm) {
        switch (algorithm) {
            case "直接插入排序":
                directInsertionSort1(array);
                break;
            case "希尔排序":
                shellSort1(array);
                break;
            case "冒泡排序":
                improvedBubbleSort1(array);
                break;
            case "快速排序":
                quickSort1(array, 0, array.length - 1);
                break;
            case "直接选择排序":
                directSelectionSort1(array);
                break;
            case "堆排序":
                heapSort1(array);
                break;
            case "归并排序":
                mergeSort1(array, 0, array.length - 1);
                break;
            case "链式基数排序":
                radixSort1(array);
                break;
            // 添加其他算法的情况...
            default:
                // 处理未知算法
                break;
        }
        return array; // 返回排序后的数组
    }

    private void writeResultToFile(String fileName, int[] sortedArray) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("排序结果：" + Arrays.toString(sortedArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
