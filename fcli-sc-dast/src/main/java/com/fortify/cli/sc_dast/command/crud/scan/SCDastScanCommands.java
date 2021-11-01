package com.fortify.cli.sc_dast.command.crud.scan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fortify.cli.common.picocli.annotation.SubcommandOf;
import com.fortify.cli.common.picocli.component.output.OutputOptionsHandler;
import com.fortify.cli.sc_dast.command.AbstractSCDastUnirestRunnerCommand;
import com.fortify.cli.sc_dast.command.crud.SCDastCrudRootCommands;
import com.fortify.cli.sc_dast.command.crud.scan.options.SCDastGetScanListOptions;
import com.fortify.cli.sc_dast.command.crud.scan.options.SCDastGetScanOptions;

import io.micronaut.core.annotation.ReflectiveAccess;
import kong.unirest.UnirestInstance;
import lombok.Getter;
import lombok.SneakyThrows;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;


public class SCDastScanCommands {
    static final String NAME = "scan";
    static final String DESC = "DAST scan";

    @ReflectiveAccess
    @SubcommandOf(SCDastCrudRootCommands.SCDastGetCommand.class)
    @Command(name = NAME, description = "Get " + DESC + " from SC DAST")
    public static final class Get extends AbstractSCDastUnirestRunnerCommand {

        @ArgGroup(exclusive = false, heading = "Get a specific scan:%n", order = 1)
        @Getter private SCDastGetScanOptions scanOptions;


        @ArgGroup(exclusive = false, heading = "Filter multiple scans:%n", order = 2)
        @Getter private SCDastGetScanListOptions scanListOptions;

        @Mixin
        @Getter private OutputOptionsHandler outputOptionsHandler;

        @SneakyThrows
        protected Void runWithUnirest(UnirestInstance unirest) {
            String urlPath = "/api/v2/scans/scan-summary-list";
            String urlParams = "";
            String dataNode;

            if (scanOptions != null){
                urlPath = "/api/v2/scans/"+ scanOptions.getScanId() + "/scan-summary";
                dataNode = "item";
            }
            else {
                dataNode = "items";
                if(scanListOptions != null) {
                    if (scanListOptions.getSearchText() != null) {
                        urlParams += String.format("searchText=%s&",scanListOptions.getSearchText());
                    }
                    if(scanListOptions.getStartDate() != null) {
                        urlParams += String.format("startedOnStartDate=%s&",scanListOptions.getStartDate());
                    }
                    if(scanListOptions.getEndDate() != null) {
                        urlParams += String.format("startedOnEndDate=%s&",scanListOptions.getEndDate());
                    }
                    if(scanListOptions.getOrderBy() != null) {
                        urlParams += String.format("orderBy=%s&",scanListOptions.getOrderBy());
                    }
                    if(scanListOptions.getOrderByDirection() != null) {
                        urlParams += String.format("orderByDirection=%s&",scanListOptions.getOrderByDirection());
                    }
                    if(scanListOptions.getScanStatus() != null) {
                        urlParams += String.format("scanStatusType=%s&",scanListOptions.getScanStatus());
                    }
                }
            }

            JsonNode response = unirest.get(urlPath + "?" + urlParams)
                    .accept("application/json")
                    .header("Content-Type", "application/json")
                    .asObject(ObjectNode.class)
                    .getBody()
                    .get(dataNode);

            outputOptionsHandler.write(response);

            return null;
        }
    }
}