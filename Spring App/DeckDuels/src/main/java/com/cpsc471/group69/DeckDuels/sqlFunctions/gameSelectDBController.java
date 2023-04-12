package com.cpsc471.group69.DeckDuels.sqlFunctions;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class gameSelectDBController {

    @GetMapping("/infoget/getdeck/{username}")
    public List<String> getDeck(@PathVariable(value = "username")){

        return new ArrayList<>()
    }

}
