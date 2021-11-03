package com.fortify.cli.sc_dast.command.dast.scan.remote.options;

import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.Getter;
import picocli.CommandLine.Option;

/**
 * Configure options for running SC DAST Scan
 *
 * @author Ruud Senden
 */
@ReflectiveAccess
public class SCDastScanPublishOptions {

    @Option(names = {"-id","--scan-id"}, description = "The scan id.", required = true)
    @Getter private int scanId;
}