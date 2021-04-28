import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bayMax
 * @date 2021/4/27 15:05
 */
public class FictionScript {

    static Map<Integer, String[]> catalogueMap = new HashMap<>();

    static Integer num = 0;

    public static void main(String[] args) throws IOException {
        Scanner text = new Scanner(System.in);
        System.out.print("输入书名：");
        String name = text.next();
        System.out.println("正在获取书籍资料请等待...");
        String searchUrl = "https://so.biqusoso.com/s1.php?ie=utf-8&siteid=booktxt.net&q=" + URLEncoder.encode(name, "UTF-8");
        String searchContent = new HttpRequest(searchUrl).method(Method.GET).timeout(-1).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36").execute().body();
        String contentUrl = getResultByReg(searchContent, "s2\"><a href=\"([^,]+?)\"");
        if (StringUtils.isBlank(contentUrl)) {
            System.out.println("没找到对应的书籍！关了昂！没写循环！重启吧！");
            return;
        }
        String url1 = new HttpRequest(contentUrl).method(Method.GET).timeout(-1).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36").execute().headers().get("Location").get(0);
        String url2 = new HttpRequest(url1).method(Method.GET).timeout(-1).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36").execute().headers().get("Location").get(0);
        String url3 = new HttpRequest(url2).method(Method.GET).timeout(-1).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36").execute().headers().get("Location").get(0);
        String content = HttpUtil.get(url3);
        if (StringUtils.isNotBlank(content)) {
            System.out.println("默认获取查找到的第一本书！");
            String contents = getResultByReg(content, "正文</dt>(.*?)</dl>");
            String[] catalogue = Objects.requireNonNull(contents).split("<dd>");
            for (int i = 1; i < catalogue.length; i++) {
                String[] details = new String[2];
                details[0] = getResultByReg(catalogue[i], "\">(.*?)</a>");
                details[1] = url3 + getResultByReg(catalogue[i], "<a href =\"([^,]+?)\"");
                catalogueMap.put(i, details);
            }
        } else {
            System.out.println("没找到！");
        }
        if (!catalogueMap.isEmpty()) {
            catalogueMap.forEach((k, v) -> System.out.println("序号：" + k + "-章节:" + v[0]));
            boolean isNext = true;
            while (isNext) {
                System.out.print("================================>[1]-选择章节，[2]-上一章，[3]-下一章，[4]-代码模式(输入任意值后继续)，[其他]-退出，请输入：");
                String type = text.next();
                switch (type) {
                    case "1":
                    case "2":
                    case "3":
                        sysoContent(type);
                        break;
                    case "4":
                        codePattern();
                        break;
                    default:
                        System.out.println("退出！");
                        isNext = false;
                        break;
                }
            }
        } else {
            System.out.println("没找到！");
        }
    }

    public static void codePattern() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("public class HeapSort {\n" +
                "\n" +
                "    /**\n" +
                "     * 堆排序\n" +
                "     * @param arr\n" +
                "     * @return\n" +
                "     */\n" +
                "    public static int[] sort(int[] arr, int n) {\n" +
                "        if (n <= 0) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        int i;\n" +
                "        // 建堆 (i等于根节点(最顶父节点))\n" +
                "        for (i = n / 2 - 1; i >= 0; i--) {\n" +
                "            heapify(arr, n, i);\n" +
                "        }\n" +
                "        // 排序\n" +
                "        for (i = n - 1; i > 0; i--) {\n" +
                "            swap(arr, i, 0);\n" +
                "            heapify(arr, i, 0);\n" +
                "        }\n" +
                "        return arr;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 维护堆的性质\n" +
                "     * @param arr\n" +
                "     * @param n\n" +
                "     * @param i\n" +
                "     * @return\n" +
                "     */\n" +
                "    public static void heapify(int[] arr, int n, int i) {\n" +
                "        int largest = i;\n" +
                "        int lson = i * 2 + 1;\n" +
                "        int rson = lson + 1;\n" +
                "\n" +
                "        if (lson < n && arr[largest] < arr[lson]) {\n" +
                "            largest = lson;\n" +
                "        }\n" +
                "        if (rson < n && arr[largest] < arr[rson]) {\n" +
                "            largest = rson;\n" +
                "        }\n" +
                "        if (largest != i) {\n" +
                "            // 左右节点有比largest大,进行交换\n" +
                "            swap(arr, largest, i);\n" +
                "            // 递归维护堆的性质\n" +
                "            heapify(arr, n, largest);\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 交换位置\n" +
                "     */\n" +
                "    public static void swap(int[] arr, int low, int high) {\n" +
                "        int temp = arr[low];\n" +
                "        arr[low] = arr[high];\n" +
                "        arr[high] = temp;\n" +
                "    }\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        int[] arr = {9, 5, 3, 6, 8, 1, 2, 3, 11, 52, 32, 11, 23};\n" +
                "        System.out.println(Arrays.toString(sort(arr, arr.length)));\n" +
                "    }\n" +
                "}");
        new Scanner(System.in).next();
    }

    public static void sysoContent(String type) {
        switch (type) {
            case "2":
                num = num - 1;
                break;
            case "3":
                num = num + 1;
                break;
            case "1":
            default:
                Scanner number = new Scanner(System.in);
                System.out.print("输入你要看的章节：");
                num = Integer.parseInt(number.next());
                break;
        }
        if (!catalogueMap.containsKey(num)) {
            System.out.println("没有了！");
            return;
        }
        String[] details = catalogueMap.get(num);
        System.out.print("你选择的章节：" + details[0]);
        String detail = HttpUtil.get(details[1], Charset.forName("GBK"));
        String name = getResultByReg(detail, "<h1>(.*?)</h1>");
        System.out.println(name);
        String content = getResultByReg(detail, "content\"><br /><br />(.*?)<br /><br /><script>chaptererror()");
        content = Objects.requireNonNull(content).replaceAll("&nbsp;", "");
        String[] contents = content.split("<br />");
        for (String c : contents) {
            if (StringUtils.isNotBlank(c)) {
                System.out.println(c.replaceAll("#送888现金红包#关注vx.公众号【书友大本营】，看热门神作，抽888现金红包！", ""));
            }
        }
    }

    public static String getResultByReg(String content, String reg) {
        List<String> list = new ArrayList<>();
        Pattern pa = Pattern.compile(reg, Pattern.DOTALL);
        Matcher ma1 = pa.matcher(content);
        if (ma1.find()) {
            list.add(ma1.group(1));
            return list.get(0);
        } else {
            return null;
        }
    }

}
