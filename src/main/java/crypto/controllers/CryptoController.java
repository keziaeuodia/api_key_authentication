package crypto.controllers;

import crypto.exception.APIKeyNotFoundException;
import crypto.exception.GlobalExceptionHandler;
import crypto.models.CryptoRoot;
import crypto.models.HistoCrypto;
import crypto.models.user.User;
import crypto.services.CryptoService;
import crypto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/data")

public class CryptoController {

    @Autowired
    CryptoService cryptoService;

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    //making 3rd party API call from CryptoCompare, persist is optional
    @RequestMapping("/histominute")
    public CryptoRoot search (@RequestParam(value = "fsym", required = true) String fsym,
                              @RequestParam(value = "tsym", required = true) String tsym,
                              @RequestParam(value = "persist", defaultValue = "false") boolean persist){
        return cryptoService.search(fsym, tsym, persist);
    }

    //get crypto data by "from" currency
    @RequestMapping(method= RequestMethod.GET, value = "/fsym")
    public ArrayList<HistoCrypto> findByFsym(@RequestParam(value = "fsym", required = true) String fsym,
                                            @RequestParam(value = "APIKEY", required = true) String apikey){

        try {
            return cryptoService.getDataByFsym(fsym,apikey);
        } catch (APIKeyNotFoundException e) {
            System.out.println(e.getMessage());
            globalExceptionHandler.handle404();
            return null;
        }

    }

    //get crypto data by "to" currency
    @RequestMapping(method= RequestMethod.GET, value = "/tsym")
    public ArrayList<HistoCrypto> findByTsym(@RequestParam(value = "tsym", required = true) String tsym,
                                    @RequestParam(value = "APIKEY", required = true) String apikey){
        return cryptoService.getDataByTsym(tsym);
    }

    //get crypto data by "id"
    @GetMapping("/{id}")
    public HistoCrypto getDataById(@PathVariable(value = "id") int id,
                                   @RequestParam(value = "APIKEY", required = true) String apikey){
        return cryptoService.getDataById(id);
    }

    //get all crypto data from the database
    @GetMapping("/")
    public ArrayList<HistoCrypto> findAll(){
        return cryptoService.getAllData();
    }

    //post new crypto data to DB
    @PostMapping("/")
    public String add(@RequestBody HistoCrypto data,
                      @RequestParam(value = "APIKEY", required = true) String apikey){
        return cryptoService.addData(data);
    }

    //edit data in DB by ID
    @PatchMapping("/")
    public HistoCrypto update(@RequestBody HistoCrypto data,
                              @RequestParam(value = "APIKEY", required = true) String apikey) {
        return cryptoService.update(data);
    }

    //delete data in DB by ID
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(value = "id") int id,
                             @RequestParam(value = "APIKEY", required = true) String apikey){
        return cryptoService.deleteDataById(id);
    }

}
