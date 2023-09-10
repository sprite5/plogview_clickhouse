package vip.zhaoxing.plogview.util;

import com.clickhouse.client.*;
import com.clickhouse.client.config.ClickHouseClientOption;
import com.clickhouse.data.ClickHouseFormat;
import org.springframework.stereotype.Service;
import vip.zhaoxing.plogview.config.CkConfig;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class CkHttpClientUtil {

    private ClickHouseNodes servers;
    private static ClickHouseClient client;

    @PostConstruct
    private void init(){
        servers = ClickHouseNodes.of(CkConfig.getUrl());
        var credentials = ClickHouseCredentials.fromUserAndPassword(CkConfig.getUser(),CkConfig.getPasswd());
        client =  ClickHouseClient.builder().defaultCredentials(credentials)
                .nodeSelector(ClickHouseNodeSelector.of(null, ClickHouseProtocol.HTTP))
                .options(Map.of(ClickHouseClientOption.FORMAT,ClickHouseFormat.JSONEachRow
                        ,ClickHouseClientOption.DATABASE,CkConfig.getDatabase()))
                .build();
    }



    public  ClickHouseResponse  search(String sql,String ...params){
        ClickHouseResponse resp = null;
        try {
            resp = client.read(servers)
                    .query(sql)
                    .params(params)
                    .executeAndWait();
        }catch (ClickHouseException e){
            return null;
        }
        return resp;
    }

    public ClickHouseResponse  update(String sql,String ...params) throws ClickHouseException{
        var resp = client.read(servers)
                .write()
                .query(sql)
                .params(params)
                .executeAndWait();
        return resp;
    }
}
