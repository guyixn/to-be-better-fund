package com.collect_fund.eastmoney;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @Author: tbb
 * @Date: 2022/2/15 22:35
 * @Description:
 */
@Slf4j
public class ToTestParse {
    public static void main(String[] args) {
        String t = "<table>\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th class=\\\"xz\\\" rowspan=\\\"2\\\">选择<br />比较</th>\n" +
                "                <th class=\\\"xh\\\" rowspan=\\\"2\\\">序号</th>\n" +
                "                <th class=\\\"dm\\\" rowspan=\\\"2\\\"><a href=\\\"http://fund.eastmoney.com/dingtou/dtbj_bzdm.html\\\"\n" +
                "                        target=\\\"_self\\\">代码</a></th>\n" +
                "                <th class=\\\"jc\\\" rowspan=\\\"2\\\"><a href=\\\"http://fund.eastmoney.com/dingtou/dtbj_jjjc.html\\\"\n" +
                "                        target=\\\"_self\\\">基金简称</a></th>\n" +
                "                <th class=\\\"xglj\\\" rowspan=\\\"2\\\">相关链接</th>\n" +
                "                <th class=\\\"dwjz desc\\\" rowspan=\\\"2\\\"><a>单位净值</a></th>\n" +
                "                <th class=\\\"rq\\\" rowspan=\\\"2\\\">日期</th>\n" +
                "                <th colspan=\\\"2\\\">近1年</th>\n" +
                "                <th colspan=\\\"2\\\">近2年</th>\n" +
                "                <th colspan=\\\"2\\\">近3年</th>\n" +
                "                <th colspan=\\\"2\\\">近5年</th>\n" +
                "                <th rowspan=\\\"2\\\" class=\\\"cz\\\">操作</th>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <th class=\\\"yndt\\\"><a href=\\\"http://fund.eastmoney.com/dingtou/dtbj_yndt.html\\\" target=\\\"_self\\\">定投</a>\n" +
                "                </th>\n" +
                "                <th class=\\\"yndt\\\"><a href=\\\"http://fund.eastmoney.com/dingtou/dtbj_yndb.html\\\" target=\\\"_self\\\">单笔</a>\n" +
                "                </th>\n" +
                "                <th class=\\\"endt\\\"><a href=\\\"http://fund.eastmoney.com/dingtou/dtbj_endt.html\\\" target=\\\"_self\\\">定投</a>\n" +
                "                </th>\n" +
                "                <th class=\\\"endt\\\"><a href=\\\"http://fund.eastmoney.com/dingtou/dtbj_endb.html\\\" target=\\\"_self\\\">单笔</a>\n" +
                "                </th>\n" +
                "                <th class=\\\"sndt\\\"><a href=\\\"http://fund.eastmoney.com/dingtou/dtbj_sndt.html\\\" target=\\\"_self\\\">定投</a>\n" +
                "                </th>\n" +
                "                <th class=\\\"sndt\\\"><a href=\\\"http://fund.eastmoney.com/dingtou/dtbj_sndb.html\\\" target=\\\"_self\\\">单笔</a>\n" +
                "                </th>\n" +
                "                <th class=\\\"wndt\\\"><a href=\\\"http://fund.eastmoney.com/dingtou/dtbj_wndt.html\\\" target=\\\"_self\\\">定投</a>\n" +
                "                </th>\n" +
                "                <th class=\\\"wndt\\\"><a href=\\\"http://fund.eastmoney.com/dingtou/dtbj_wndb.html\\\" target=\\\"_self\\\">单笔</a>\n" +
                "                </th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td><input type=\\\"checkbox\\\" value=\\\"350005,天治中国制造2025\\\" id=\\\"chk350005\\\"\n" +
                "                        onclick=\\\"_$fn_check(this)\\\" /></td>\n" +
                "                <td>101</td>\n" +
                "                <td><a href=\\\"http://fund.eastmoney.com/350005.html\\\">350005</a></td>\n" +
                "                <td><a href=\\\"http://fund.eastmoney.com/350005.html\\\">天治中国制造2025</a></td>\n" +
                "                <td class=\\\"xglj\\\"><a href=\\\"http://fund.eastmoney.com/350005.html\\\" class=\\\"red\\\">估值图</a><a\n" +
                "                        href=\\\"http://jijinba.eastmoney.com/list,350005.html\\\">基金吧</a><a\n" +
                "                        href=\\\"http://fundf10.eastmoney.com/350005.html\\\">档案</a></td>\n" +
                "                <td><span class=\\\"ping\\\">4.3433</span></td>\n" +
                "                <td>02-15</td>\n" +
                "                <td><span class=\\\"die\\\">-2.76%</span></td>\n" +
                "                <td><span class=\\\"die\\\">-4.94%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">17.25%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">70.87%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">45.79%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">132.45%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">81.42%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">180.39%</span></td>\n" +
                "                <td><a class='btn dingtou' href='https://trade.1234567.com.cn/Investment/add.aspx?fc=350005'>定投</a></td>\n" +
                "            </tr>\n" +
                "            <tr class=\\\"odd\\\">\n" +
                "                <td><input type=\\\"checkbox\\\" value=\\\"260101,景顺长城优选混合\\\" id=\\\"chk260101\\\" onclick=\\\"_$fn_check(this)\\\" />\n" +
                "                </td>\n" +
                "                <td>102</td>\n" +
                "                <td><a href=\\\"http://fund.eastmoney.com/260101.html\\\">260101</a></td>\n" +
                "                <td><a href=\\\"http://fund.eastmoney.com/260101.html\\\">景顺长城优选混合</a></td>\n" +
                "                <td class=\\\"xglj\\\"><a href=\\\"http://fund.eastmoney.com/260101.html\\\" class=\\\"red\\\">估值图</a><a\n" +
                "                        href=\\\"http://jijinba.eastmoney.com/list,260101.html\\\">基金吧</a><a\n" +
                "                        href=\\\"http://fundf10.eastmoney.com/260101.html\\\">档案</a></td>\n" +
                "                <td><span class=\\\"ping\\\">4.3364</span></td>\n" +
                "                <td>02-15</td>\n" +
                "                <td><span class=\\\"die\\\">-1.87%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">8.03%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">13.58%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">52.06%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">40.04%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">141.70%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">71.10%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">156.32%</span></td>\n" +
                "                <td><a class='btn dingtou' href='https://trade.1234567.com.cn/Investment/add.aspx?fc=260101'>定投</a></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td><input type=\\\"checkbox\\\" value=\\\"519918,华夏兴和混合\\\" id=\\\"chk519918\\\" onclick=\\\"_$fn_check(this)\\\" />\n" +
                "                </td>\n" +
                "                <td>103</td>\n" +
                "                <td><a href=\\\"http://fund.eastmoney.com/519918.html\\\">519918</a></td>\n" +
                "                <td><a href=\\\"http://fund.eastmoney.com/519918.html\\\">华夏兴和混合</a></td>\n" +
                "                <td class=\\\"xglj\\\"><a href=\\\"http://fund.eastmoney.com/519918.html\\\" class=\\\"red\\\">估值图</a><a\n" +
                "                        href=\\\"http://jijinba.eastmoney.com/list,519918.html\\\">基金吧</a><a\n" +
                "                        href=\\\"http://fundf10.eastmoney.com/519918.html\\\">档案</a></td>\n" +
                "                <td><span class=\\\"ping\\\">4.3270</span></td>\n" +
                "                <td>02-15</td>\n" +
                "                <td><span class=\\\"zhang\\\">21.34%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">50.61%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">60.95%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">142.95%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">105.92%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">242.60%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">146.74%</span></td>\n" +
                "                <td><span class=\\\"zhang\\\">194.55%</span></td>\n" +
                "                <td><a class='btn dingtou' href='https://trade.1234567.com.cn/Investment/add.aspx?fc=519918'>定投</a></td>\n" +
                "            </tr>\n" +
                "            \n" +
                "        </tbody>\n" +
                "    </table>";

        //Document doc = Jsoup.connect("https://fund.eastmoney.com/Company/default.html").get();
        //        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        //        log.info(doc.title());
        //        Elements newsHeadlines = doc.select("#gspmTbl > tbody > tr");
        //        for (Element headline : newsHeadlines) {
        //            log.info(headline.select("td:nth-child(2)").text());
        //        }
        Document doc = Jsoup.parseBodyFragment(t);
        Element body = doc.body();
        Elements resultLinks = body.select("table > tbody > tr");
        for (Element headline : resultLinks) {
            StringBuilder sb = new StringBuilder();
            sb.append(headline.select("td:nth-child(3)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(4)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(6)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(7)").text()).append(" || ");

            sb.append(headline.select("td:nth-child(8)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(9)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(10)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(11)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(12)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(13)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(14)").text()).append(" || ");
            sb.append(headline.select("td:nth-child(15)").text()).append(" || ");

            log.info(sb.toString());
        }

    }
}
