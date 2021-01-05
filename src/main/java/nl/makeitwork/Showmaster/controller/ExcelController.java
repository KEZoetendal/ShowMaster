package nl.makeitwork.Showmaster.controller;

import io.github.millij.poi.SpreadsheetReadException;
import io.github.millij.poi.ss.reader.XlsReader;
import io.github.millij.poi.ss.reader.XlsxReader;
import nl.makeitwork.Showmaster.model.Voorstelling;
import nl.makeitwork.Showmaster.repository.VoorstellingRepository;
import nl.makeitwork.Showmaster.service.VoorstellingsTaakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Karin Zoetendal
 * deze klasse zorgt ervoor dat er Excel bestanden kunnen worden geselecteerd, geupdated en uitgelezen
 * op die manier kunnen voorstellingen worden ingelezen
 */

@Controller
public class ExcelController {

    @Autowired
    private VoorstellingRepository voorstellingRepository;
    @Autowired
    @Qualifier("voorstellingsTaakService")
    private VoorstellingsTaakService voorstellingsTaakService;

    private String fileLocation;


    @GetMapping("/planner/voorstellingen/excel")
    public String getExcelProcessingPage() {
        return "excel";
    }


    @PostMapping("/planner/voorstellingen/excel/upload")
    public String uploadFile(Model model, MultipartFile file) throws IOException {
        InputStream in = file.getInputStream();
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
        FileOutputStream f = new FileOutputStream(fileLocation);
        int ch;
        while ((ch = in.read()) != -1) {
            f.write(ch);
        }
        f.flush();
        f.close();
        model.addAttribute("message", "Uploaden van bestand: " + file.getOriginalFilename()
                + " is geslaagd.");
        return "excelGeslaagd";
    }


    @GetMapping("/planner/voorstellingen/excel/toevoegen")
    public String excelVoorstellingToevoegen(Model model) throws SpreadsheetReadException {

        if (fileLocation != null) {
            if (fileLocation.endsWith(".xlsx")) {
                readExcelXlsx(fileLocation);
            } else if (fileLocation.endsWith(".xls")) {
                readExcelXls(fileLocation);
            } else {
                model.addAttribute("message", "Geen geschikt Excelbestand!");
            }
        } else {
            model.addAttribute("message", "Geen bestand beschikbaar! A.u.b. een Excelbestand uploaden.");
        }
        return "redirect:/planner/voorstellingen";
    }


    public void readExcelXlsx(String fileLocation) throws SpreadsheetReadException {

        final File xlsxFile = new File(fileLocation);
        final XlsxReader reader = new XlsxReader();
        List<Voorstelling> voorstellingen = reader.read(Voorstelling.class, xlsxFile);
        for (Voorstelling nieuweVoorstelling : voorstellingen) {

            nieuweVoorstelling.datumStringFormatterenNaarLocalDateTime();

            if (voorstellingRepository.findByNaam(nieuweVoorstelling.getNaam()) != null
                    && voorstellingRepository.findByLocalDateTime(nieuweVoorstelling.getLocalDateTime()) != null) {
            } else {
                voorstellingsTaakService.voorstellingOpslaanInclTaken(nieuweVoorstelling);
            }
        }
    }


    public void readExcelXls(String fileLocation) throws SpreadsheetReadException {

        final File xlsFile = new File(fileLocation);
        final XlsReader reader = new XlsReader();
        List<Voorstelling> voorstellingen = reader.read(Voorstelling.class, xlsFile);
        for (Voorstelling nieuweVoorstelling : voorstellingen) {

            nieuweVoorstelling.datumStringFormatterenNaarLocalDateTime();

            if (voorstellingRepository.findByNaam(nieuweVoorstelling.getNaam()) != null
                    && voorstellingRepository.findByLocalDateTime(nieuweVoorstelling.getLocalDateTime()) != null) {
            } else {
                voorstellingsTaakService.voorstellingOpslaanInclTaken(nieuweVoorstelling);
            }
        }
    }
}
