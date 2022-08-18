/*******************************************************************************
 * (c) Copyright 2021 Micro Focus or one of its affiliates
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the 
 * "Software"), to deal in the Software without restriction, including without 
 * limitation the rights to use, copy, modify, merge, publish, distribute, 
 * sublicense, and/or sell copies of the Software, and to permit persons to 
 * whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY 
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 ******************************************************************************/
package com.fortify.cli.ssc.picocli.command.report_template;

import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.SneakyThrows;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@ReflectiveAccess
@Command(name = "generate-answerFile", aliases = {"gen-answer"})
public class SSCReportTemplateGenerateAnswerFileCommand implements Runnable {
	String templateFileName = "ReportTemplateDefAnswerTemplate.yml";
	@CommandLine.Option(names = {"-o", "--output"}, defaultValue = "./ReportTemplateDefAnswerTemplate.yml")
	private String filePath;

	/**
	 * Create a template json file (the answer file) so that the user can input the needed information in to that for
	 * the purposes of creating a new report template definition. This is an alternative to using the "create" command
	 * with a ton of options, parameters, and whatnot.
	 */
	@SneakyThrows
	@Override
	public void run() {
		InputStream myFile = this.getClass().getClassLoader().getResourceAsStream("com/fortify/cli/ssc/report-template/" + templateFileName);
		Files.copy(myFile, new File(filePath).toPath() , REPLACE_EXISTING);
		System.out.println("DONE.");
	}
}