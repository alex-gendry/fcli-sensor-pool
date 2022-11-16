package com.fortify.cli.tool.sc_client.cli.cmd;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.fortify.cli.common.output.cli.mixin.BasicOutputHelperMixins;
import com.fortify.cli.common.util.StringUtils;
import com.fortify.cli.tool.common.cli.cmd.AbstractToolInstallCommand;
import com.fortify.cli.tool.common.helper.ToolInstallDescriptor.ToolVersionInstallDescriptor;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = BasicOutputHelperMixins.Install.CMD_NAME)
public class ToolSCClientInstallCommand extends AbstractToolInstallCommand {
    @Option(names= {"-t", "--client-auth-token"}) private String clientAuthToken; 

    @Override
    protected String getToolName() {
        return "sc-client";
    }
    
    @Override
    protected InstallType getInstallType() {
        return InstallType.EXTRACT_ZIP;
    }
    
    @Override
    protected void postInstall(ToolVersionInstallDescriptor descriptor, Path installPath, Path binPath) throws IOException {
        // Updating bin permissions is handled by parent class
        updateClientAuthToken(installPath);
    }
    
    private void updateClientAuthToken(Path installPath) throws IOException {
        if ( StringUtils.isNotBlank(clientAuthToken) ) {
            Path clientPropertiesPath = installPath.resolve("Core/config/client.properties");
            Files.writeString(clientPropertiesPath, 
                    String.format("client_auth_token=%s", clientAuthToken), 
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
