package com.example.geektrust.services;

import com.example.geektrust.entities.MutualFund;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OverlapServiceTest {
    @Test
    void givenMfs_whenCalculatingOverlapping_thenPrintOverlappings() {
        Set<MutualFund> mfs = ExchangeService.getInstance().getMutualFunds();
        MutualFund mf = ExchangeService.getInstance().getMutualFundByName("PARAG_PARIKH_FLEXI_CAP").get();
        Optional<MutualFund> axisBluechipMf = ExchangeService.getInstance().getMutualFunds().stream().filter(_mf -> _mf.getFundName().equals("AXIS_BLUECHIP")).findFirst();
        axisBluechipMf.get().getStocks().add("AXIS_BLUECHIP");

        String expected = "PARAG_PARIKH_FLEXI_CAP ICICI_PRU_NIFTY_NEXT_50_INDEX 7.41%\n" +
                "PARAG_PARIKH_FLEXI_CAP PARAG_PARIKH_CONSERVATIVE_HYBRID 14.74%\n" +
                "PARAG_PARIKH_FLEXI_CAP UTI_NIFTY_INDEX 25.29%\n" +
                "PARAG_PARIKH_FLEXI_CAP AXIS_MIDCAP 16.09%\n" +
                "PARAG_PARIKH_FLEXI_CAP MIRAE_ASSET_EMERGING_BLUECHIP 29.21%\n" +
                "PARAG_PARIKH_FLEXI_CAP PARAG_PARIKH_FLEXI_CAP 100.00%\n" +
                "PARAG_PARIKH_FLEXI_CAP MIRAE_ASSET_LARGE_CAP 23.66%\n" +
                "PARAG_PARIKH_FLEXI_CAP SBI_LARGE_&_MIDCAP 2.41%\n" +
                "PARAG_PARIKH_FLEXI_CAP AXIS_BLUECHIP 18.75%\n" +
                "PARAG_PARIKH_FLEXI_CAP ICICI_PRU_BLUECHIP 22.68%\n";


        StringBuilder result = OverlapService.calculateOverlapping(mfs, mf);
        assertEquals(expected, result.toString());
    }
}