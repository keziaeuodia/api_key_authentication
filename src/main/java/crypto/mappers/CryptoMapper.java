package crypto.mappers;

import crypto.models.HistoCrypto;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CryptoMapper {

    String INSERT_DATA = "INSERT INTO `Api_key_auth`.`exchange_summary`(`id`,`fromCurrency`,`toCurrency`,`time`,`open`,`high`,`low`,`close`)\n" +
            "VALUES(#{id},#{fromCurrency},#{toCurrency},#{time},#{open},#{high},#{low},#{close});";
    String GET_ALL_DATA = "SELECT * FROM `Api_key_auth`.`exchange_summary`;";
    String GET_DATA_BY_FSYM = "SELECT * FROM `Api_key_auth`.`exchange_summary` WHERE `fromCurrency` = #{fromCurrency};";
    String GET_DATA_BY_TSYM = "SELECT * FROM `Api_key_auth`.`exchange_summary` WHERE `toCurrency` = #{toCurrency};";
    String GET_DATA_BY_ID = "SELECT * FROM `Api_key_auth`.`exchange_summary` WHERE `id` = #{id};";
    String DELETE_DATA_BY_ID = "DELETE FROM `Api_key_auth`.`exchange_summary` WHERE `id` = #{id};";
    String UPDATE_DATA_BY_ID = "UPDATE `Api_key_auth`.`exchange_summary`SET`id` = #{id},`fromCurrency` = #{fromCurrency}," +
            "`toCurrency` = #{toCurrency},`time` = #{time},`open` = #{open},`high` = #{high},`low` = #{low},`close` = #{close}" +
            "WHERE `id` = #{id};";

    @Insert(INSERT_DATA)
    public void saveCryptoData(HistoCrypto obj);

    @Select(GET_ALL_DATA)
    public ArrayList<HistoCrypto> getAllData();

    @Select(GET_DATA_BY_FSYM)
    public ArrayList<HistoCrypto> getDataByFsym(String fsym);

    @Select(GET_DATA_BY_TSYM)
    public ArrayList<HistoCrypto> getDataByTsym(String tsym);

    @Select(GET_DATA_BY_ID)
    HistoCrypto getDataById(int id);

    @Delete(DELETE_DATA_BY_ID)
    public void deleteDataById(int id);

    @Update(UPDATE_DATA_BY_ID)
    public int update(HistoCrypto histoCrypto);


}
