package pl.coderslab.endingproject.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.endingproject.dao.LoadingDao;
import pl.coderslab.endingproject.entity.Loading;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/palette")
public class LoadingController {

    private LoadingDao loadingDao;

    public LoadingController(LoadingDao loadingDao) {
        this.loadingDao = loadingDao;
    }

    @GetMapping("/add")
    public String formAdd(Model model) {
        Loading loading = new Loading();
        model.addAttribute("loading", loading);
        return "loading/add";
    }

    @PostMapping("/add")
    public String addLoading(
            @RequestParam String companyName,
            @RequestParam Integer loadedPallets,
            @RequestParam Integer exchangedPallets,
            @RequestParam Integer returnedPallets,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate loadingDate,
            @RequestParam String loadingNumber) {

        Loading loading = new Loading();
        loading.setCompanyName(companyName);
        loading.setLoadedPallets(loadedPallets);
        loading.setExchangedPallets(exchangedPallets);
        loading.setReturnedPallets(returnedPallets);
        loading.setLoadingDate(loadingDate);
        loading.setLoadingNumber(loadingNumber);
        loadingDao.saveLoading(loading);

        return "redirect:/palette/list";
    }


    @GetMapping("/get/{loadingId}")
    public String loadingsDetails(@PathVariable Long companyId, Model model) {
        Loading loading = loadingDao.findByIdLoading(companyId);
        if (loading != null) {
            Long companyName = loading.getCompany().getCompanyId();
            model.addAttribute("loading", loading);

            return "loading/get";
        } else {
            return "error";
        }
    }

    @GetMapping("/list")
    public String listTrucks(Model model) {
        List<Loading> loadings = loadingDao.getAllLoadings();
        model.addAttribute("loadings", loadings);
        return "loading/list";
    }

}
