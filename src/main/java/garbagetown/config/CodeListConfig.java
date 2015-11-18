package garbagetown.config;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.terasoluna.gfw.common.codelist.JdbcCodeList;
import org.terasoluna.gfw.common.codelist.NumberRangeCodeList;
import org.terasoluna.gfw.common.codelist.i18n.SimpleI18nCodeList;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.web.codelist.CodeListInterceptor;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.*;

/**
 * Created by garbagetown on 10/13/15.
 */
@Configuration
public class CodeListConfig extends WebMvcConfigurerAdapter {

    @Inject
    DataSource dataSource;

    @Inject
    JodaTimeDateFactory dateFactory;

    @Bean(name = "CL_BIRTH_YEAR")
    NumberRangeCodeList clBirthYear() {
        NumberRangeCodeList codeList = new NumberRangeCodeList();
        codeList.setFrom(1940);
        codeList.setTo(2000);
        return codeList;
    }

    @Bean(name = "CL_MONTH")
    NumberRangeCodeList clMonth() {
        NumberRangeCodeList codeList = new NumberRangeCodeList();
        codeList.setFrom(1);
        codeList.setTo(12);
        codeList.setValueFormat("%d");
        codeList.setLabelFormat("%02d");
        return codeList;
    }

    @Bean(name = "CL_DAY")
    NumberRangeCodeList clDay() {
        NumberRangeCodeList codeList = new NumberRangeCodeList();
        codeList.setFrom(1);
        codeList.setTo(31);
        codeList.setValueFormat("%d");
        codeList.setLabelFormat("%02d");
        return codeList;
    }

    @Bean(name = "CL_TOUR_DAYS")
    SimpleI18nCodeList clTourDays() {

        List<String> enLabels = Lists.newArrayList("Unlimited", "Single-day", "Within one night", "Within two nights",
                "Within three nights", "Within four nights", "Within five nights");
        List<String> jaLables = Lists.newArrayList("制限なし", "日帰り", "1泊以下", "2泊以下", "3泊以下", "4泊以下", "5泊以下");
        List<String> values = Lists.newArrayList("0", "1", "2", "3", "4", "5", "6");

        Map<String, String> en = new LinkedHashMap<>();
        Map<String, String> ja = new LinkedHashMap<>();

        for (int i = 0, n = values.size(); i < n; i++) {
            en.put(values.get(i), enLabels.get(i));
            ja.put(values.get(i), jaLables.get(i));
        }

        Map<Locale, Map<String, String>> rows = new HashMap<>();
        rows.put(Locale.ENGLISH, en);
        rows.put(Locale.JAPANESE, ja);

        SimpleI18nCodeList codeList = new SimpleI18nCodeList();
        codeList.setRows(rows);

        return codeList;
    }

    @Bean(name = "CL_DEP_YEAR")
    NumberRangeCodeList clDepYear() {
        NumberRangeCodeList codeList = new NumberRangeCodeList();

        DateTime thisYear = dateFactory.newDateTime();
        DateTime nextYear = thisYear.plusYears(1);

        codeList.setFrom(Integer.parseInt(thisYear.toString("Y")));
        codeList.setTo(Integer.parseInt(nextYear.toString("Y")));

        return codeList;
    }

    @Bean(name = "CL_PREF")
    JdbcCodeList clPref() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        jdbcTemplate.setFetchSize(1000);

        JdbcCodeList codeList = new JdbcCodeList();
        codeList.setJdbcTemplate(jdbcTemplate);
        codeList.setQuerySql("SELECT arr_code, arr_name FROM arrival ORDER BY arr_code");
        codeList.setValueColumn("arr_code");
        codeList.setLabelColumn("arr_name");
        return codeList;
    }

    @Bean(name = "CL_ADULT_COUNT")
    NumberRangeCodeList clAdultCount() {
        NumberRangeCodeList codeList = new NumberRangeCodeList();
        codeList.setFrom(1);
        codeList.setTo(5);
        return codeList;
    }

    @Bean(name = "CL_CHILD_COUNT")
    NumberRangeCodeList clChildCount() {
        NumberRangeCodeList codeList = new NumberRangeCodeList();
        codeList.setFrom(1);
        codeList.setTo(5);
        return codeList;
    }

    @Bean(name = "CL_BASE_PRICE")
    SimpleI18nCodeList clBasePrice() {

        List<String> enLabels = Lists.newArrayList("unlimited", "Less than ¥10,000", "Less than ¥20,000",
                "Less than ¥30,000", "Less than ¥40,000", "Less than ¥50,000");
        List<String> jaLables = Lists.newArrayList("上限なし", "10,000円以下", "20,000円以下", "30,000円以下",
                "40,000円以下", "50,000円以下");
        List<String> values = Lists.newArrayList("0", "10000", "20000", "30000", "40000", "50000");

        Map<String, String> en = new LinkedHashMap<>();
        Map<String, String> ja = new LinkedHashMap<>();

        for (int i = 0, n = values.size(); i < n; i++) {
            en.put(values.get(i), enLabels.get(i));
            ja.put(values.get(i), jaLables.get(i));
        }

        Map<Locale, Map<String, String>> rows = new HashMap<>();
        rows.put(Locale.ENGLISH, en);
        rows.put(Locale.JAPANESE, ja);

        SimpleI18nCodeList codeList = new SimpleI18nCodeList();
        codeList.setRows(rows);

        return codeList;
    }


    @Bean
    HandlerInterceptor codeListInterceptor() {
        CodeListInterceptor interceptor = new CodeListInterceptor();
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(codeListInterceptor()).addPathPatterns("/**");
    }
}
