package com.kollect.etl.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RunBatchController {
    /**
     * HTTP GET request to retrieve all batches
     *
     * @return runbatch - used to return the HTML for first time visit.
     */
    @GetMapping("/runpbkbatch")
    public Object runPbkBatch() {
        return "runPbkBatch";
    }

    @GetMapping("/runpelitabatch")
    public Object runPelitaBatch() {
        return "runPelitaBatch";
    }

    @GetMapping("/runyycbatch")
    public Object runYycBatch() {
        return "runYycBatch";
    }

    @GetMapping("/runccobatch")
    public Object runCcoBatch() {
        return "runCcoBatch";
    }

    @GetMapping("/runictzonebatch")
    public Object runIctZoneBatch() {
        return "runIctZoneBatch";
    }

    @GetMapping("/runnationwidebatch")
    public Object runNationwideBatch() {
        return "runNationwideBatch";
    }

    @GetMapping("/runmahbbatch")
    public Object runMahbBatch() {
        return "runMahbBatch";
    }
}
