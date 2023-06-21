package top.zsmile.test.sensitive;

import lombok.extern.slf4j.Slf4j;
import top.zsmile.common.core.exception.SXException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * DFA敏感词算法实现
 */
@Slf4j
public class DfaAlgorithm {
    private static DfaAlgorithm instance;

    private static ReentrantReadWriteLock lock;

    private static Map<Character, TrieNode> map = null;

    private DfaAlgorithm() {

    }

    /**
     * 单例实现
     *
     * @return
     */
    public static DfaAlgorithm getInstance() {
        if (instance == null) {
            synchronized (DfaAlgorithm.class) {
                if (instance == null) {
                    instance = new DfaAlgorithm();
                    lock = new ReentrantReadWriteLock();
                }
            }
        }
        return instance;
    }


    /**
     * 读取文件信息，加载配置文件
     *
     * @param filePath 文件路径
     * @return
     */
    public DfaAlgorithm readFile(String filePath) {
        return readFile(new File(filePath));
    }

    /**
     * 读取文件信息，加载配置文件
     *
     * @param file 文件路径
     * @return
     */
    public DfaAlgorithm readFile(File file) {
        DfaAlgorithm instance = getInstance();
        if (!file.exists()) {
            throw new SXException("文件不存在");
        }
        if (!file.isFile()) {
            throw new SXException("路径不是一个文件");
        }
        if (!file.getName().endsWith(".txt")) {
            throw new SXException("文件加载仅支持txt格式");
        }
        resolveFile(file);
        return instance;
    }


    private void resolveFile(File file) {
        try {
            boolean b = lock.writeLock().tryLock(5, TimeUnit.SECONDS);
            if (!b) {
                throw new SXException("解析文件加锁失败");
            }
            if (map == null) {
                map = new HashMap<>();
            }
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                char[] chars = line.toCharArray();
                TrieNode trieNode = null;
                for (int i = 0; i < chars.length; i++) {
                    char aChar = chars[i];
                    if (trieNode == null) {
                        trieNode = map.get(aChar);
                        if (trieNode == null) {
                            trieNode = new TrieNode(aChar);
                            map.put(aChar, trieNode);
                        }
                    } else {
                        TrieNode trieNodeNext = trieNode.get(aChar);
                        if (trieNodeNext == null) {
                            trieNodeNext = new TrieNode(aChar, (i == chars.length - 1 ? 1 : 0));
                            trieNode.put(aChar, trieNodeNext);
                        }
                        trieNode = trieNodeNext;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new SXException("解析文件加锁异常");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new SXException("找不到文件");
        } catch (IOException e) {
            e.printStackTrace();
            throw new SXException("文件加载出错");
        }
    }

    public void check(String text) {
        char[] chars = text.toCharArray();
        StringBuilder sb = new StringBuilder();
        TrieNode trieNode = null;
        for (int i = 0; i < chars.length; i++) {

            for (int j = i; j < chars.length; j++) {
                char aChar = chars[j];
                if (trieNode == null) {
                    trieNode = map.get(aChar);
                    if (trieNode != null) {
                        sb.append(aChar);
                        if (trieNode.getIsWord() == 1) {
                            System.out.println(sb.toString());
                            sb.setLength(0);
                            trieNode = null;
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    TrieNode trieNodeNext = trieNode.get(aChar);
                    if (trieNodeNext == null) {
                        sb.setLength(0);
                        trieNode = null;
                        break;
                    } else {
                        sb.append(aChar);
                        trieNode = trieNodeNext;
                        if (trieNodeNext.getIsWord() == 1) {
                            System.out.println(sb.toString());
                            sb.setLength(0);
                            trieNode = null;
                            i = j;
                            break;
                        }
                    }
                }
            }
        }
    }

}
