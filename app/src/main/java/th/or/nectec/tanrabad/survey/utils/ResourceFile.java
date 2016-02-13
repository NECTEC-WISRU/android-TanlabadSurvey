/*
 * Copyright (c) 2016 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package th.or.nectec.tanrabad.survey.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ResourceFile {

    public static String read(String fileName) {
        return new ResourceFile().readStringFromFile(fileName);
    }

    private String readStringFromFile(String filename) {
        try {
            String result = "";
            BufferedReader br = new BufferedReader(new FileReader(getResourceFile(filename)));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
            return result;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private File getResourceFile(String fileName) throws NullPointerException {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }
}
