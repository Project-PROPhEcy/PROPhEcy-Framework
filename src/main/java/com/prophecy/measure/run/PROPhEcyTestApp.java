package com.prophecy.measure.run;

import com.prophecy.Prophecy;
import com.prophecy.database.DBAccess;
import com.prophecy.measure.types.prophecy.PROPhEcyMeasureRunner;
import com.prophecy.measure.types.prophecy.queries.tests.PROPhEcy_Q_P1_Hard;
import com.prophecy.measure.types.prophecy.queries.tests.PROPhEcy_Q_P2_Hard;
import com.prophecy.measure.types.prophecy.queries.tests.PROPhEcy_Q_P3_Hard;
import com.prophecy.measure.types.prophecy.queries.virtopt.*;
import com.prophecy.processing.Task;
import com.prophecy.processing.TaskInfo;
import com.prophecy.processing.output.TupleResults;

/**
 * Created by alpha_000 on 21.08.2014.
 */
public final class PROPhEcyTestApp {

    //----------------------------------------
    // Static Functions
    //----------------------------------------


    /**
     * The main entry point.
     * @param args The app arguments.
     */
    public static void main(String[] args) {

        while(true) {
            try {

                final DBAccess dbAccess = Prophecy.GetDBAccessManager().open(
                        "driver = oracle.jdbc.driver.OracleDriver; " +
                                "url = jdbc:oracle:thin:@vanilla.informatik.tu-cottbus.de:1521:orcl; " +
                                "user = PROQUA_TPC-H_0.01; password = proqua"
                );

                final PROPhEcyMeasureRunner runner
                        = new PROPhEcyMeasureRunner(dbAccess);

                final Task task = runner.calculate(
                        new PROPhEcy_Q_P2_Hard(), true, true);

                if (task.getData().contains(TupleResults.class)) {

                    final TupleResults results = task.getData()
                            .require(TupleResults.class);

                    System.out.println("Results: ");
                    System.out.println("-------------------------------------------------------");
                    System.out.println(results);
                }

                final TaskInfo info = task.getInfo();

                System.out.println();
                System.out.println("Information: ");
                System.out.println("-------------------------------------------------------");
                for (final String scope : info.getInfoScopes()) {
                    System.out.println(String.format("%s: %s", scope, info.getInfo(scope)));
                }

                System.out.println();
                System.out.println("Time Measure: ");
                System.out.println("-------------------------------------------------------");
                for (final String scope : info.getMeasureScopes()) {
                    System.out.println(String.format("%s: %s ms", scope, info.getMeasure(scope)));
                }
                System.out.println();
                System.out.println();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
