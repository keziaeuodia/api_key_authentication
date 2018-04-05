package crypto.services;

import crypto.exception.APIKeyNotFoundException;
import crypto.mappers.CryptoMapper;
import crypto.models.CryptoRoot;
import crypto.models.HistoCrypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class CryptoService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CryptoMapper cryptoMapper;

    @Autowired
    UserService userService;


    public CryptoRoot search(String fsym, String tsym, boolean persist) {
        String fquery = "https://min-api.cryptocompare.com/data/histominute?fsym="+fsym+"&tsym="+tsym;

        CryptoRoot data = restTemplate.getForObject(fquery, CryptoRoot.class);

        if (persist){
            saveCryptoData(data, fsym, tsym);
        }
        return data;
    }

    private void saveCryptoData(CryptoRoot data, String fsym, String tsym) {

        HistoCrypto obj = new HistoCrypto();

        obj.setFromCurrency(fsym);
        obj.setToCurrency(tsym);
        obj.setTime(data.getData()[0].getTime());
        obj.setOpen(data.getData()[0].getOpen());
        obj.setHigh(data.getData()[0].getHigh());
        obj.setLow(data.getData()[0].getLow());
        obj.setClose(data.getData()[0].getClose());

        cryptoMapper.saveCryptoData(obj);

    }

    private void saveAllDataPerMinute(CryptoRoot data, String fsym, String tsym){

        HistoCrypto obj = new HistoCrypto();

        for(int i = 0; i < data.getData().length; i++) {
            obj.setFromCurrency(fsym);
            obj.setToCurrency(tsym);
            obj.setTime(data.getData()[i].getTime());
            obj.setOpen(data.getData()[i].getOpen());
            obj.setHigh(data.getData()[i].getHigh());
            obj.setLow(data.getData()[i].getLow());
            obj.setClose(data.getData()[i].getClose());

            cryptoMapper.saveCryptoData(obj);
        }
    }

    public ArrayList<HistoCrypto> getAllData(){
        return cryptoMapper.getAllData();
    }

    public ArrayList<HistoCrypto> getDataByFsym (String fsym, String apiKey) throws APIKeyNotFoundException {
        if (apiKey.equals(userService.getAllUser().contains(apiKey))) {
            return cryptoMapper.getDataByFsym(fsym);
        }else {
            throw new APIKeyNotFoundException("Your API key is not valid");
        }
    }

    public ArrayList<HistoCrypto> getDataByTsym(String tsym) {
        return cryptoMapper.getDataByTsym(tsym);
    }

    public String addData(HistoCrypto data) {
        cryptoMapper.saveCryptoData(data);
        return "Data inserted";
    }

    public String deleteDataById(int id) {
        cryptoMapper.deleteDataById(id);
        return "Data id: " + id + " deleted.";
    }

    public HistoCrypto update(HistoCrypto data) {
        cryptoMapper.update(data);
        return cryptoMapper.getDataById(data.getId());
    }

    public HistoCrypto getDataById(int id) {
        return cryptoMapper.getDataById(id);
    }


}
