package com.snailmann.me.spring.shell.opt.cmd.impl;

import com.snailmann.me.spring.shell.opt.cmd.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wenjie.li
 */
@Slf4j
public class CommandServiceImpl implements CommandService {
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(6);
    private static final String BASH = "sh";
    private static final String BASH_PARAM = "-c";

    @Override
    public String execute(String cmd) {

        Process p = null;
        String res;
        log.debug("CommandService cmd info : {}", cmd);
        try {
            // need to pass command as bash's param,
            // so that we can compatible with commands: "echo a >> b.txt" or "bash a && bash b"
            List<String> cmds = new ArrayList<>();
            cmds.add(BASH);
            cmds.add(BASH_PARAM);
            cmds.add(cmd);
            ProcessBuilder pb = new ProcessBuilder(cmds);
            p = pb.start();

            Future<String> errorFuture = executor.submit(new ReadTask(p.getErrorStream()));
            Future<String> resFuture = executor.submit(new ReadTask(p.getInputStream()));
            int exitValue = p.waitFor();
            if (exitValue > 0) {
                log.info("exec cmd error: {} ", errorFuture.get());
                res = errorFuture.get();
            } else {
                res = resFuture.get();
            }
        } catch (Exception e) {
            log.info("exec cmd error: {} ", e.getMessage());
            res = e.getMessage();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        if (StringUtils.isNotBlank(res) && res.endsWith(System.lineSeparator())) {
            res = res.substring(0, res.lastIndexOf(System.lineSeparator()));
        }
        return res;
    }

    static class ReadTask implements Callable<String> {

        InputStream is;

        ReadTask(InputStream is) {
            this.is = is;
        }

        @Override
        public String call() throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }
}


