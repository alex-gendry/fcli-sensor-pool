package com.fortify.cli.common.rest.cli.mixin;

import io.micronaut.core.annotation.ReflectiveAccess;
import picocli.CommandLine.Command;

@Command(aliases = {"rm"} ) @ReflectiveAccess
public class DeleteAliasMixin {}