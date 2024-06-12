Remove the two dependcies (just kill the for loops) for Ecobosses. It will cause error if you ever load ecobosses, but will otherwise be fine.

Then modify POM at bottom: 

					<execution>
						<phase>package</phase>
						<goals>
							<goal>shade</goal>
						</goals>
						<configuration>
							<outputDirectory>C:\Users\maxsc\Desktop\Test Server\plugins</outputDirectory>
							<shadedArtifactAttached>true</shadedArtifactAttached>
						</configuration>
					</execution>
