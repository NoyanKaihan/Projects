package csv;

import console.ConsoleColor;
import data.*;
import exceptions.DataException;
import exceptions.DataParseException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for parsing StudyGroup
 */
public class StudyGroupParser {
    private StudyGroup studyGroup;
    public StudyGroupParser(){
    }

    /**
     * Method for parsing String into StudyGroup object
     * @param object
     * @return StudyGroup object
     * @throws DataParseException
     */
    public StudyGroup objectParse(String object) throws DataParseException, DataException {
        int id=0;
        try {
            /**
             * ArrayList for parsing
             */
            List<String> dt = new ArrayList<>(Arrays.asList(object.split(",")));
                 id = Integer.parseInt(dt.get(0));

            /**
             * ZonedDateTime parsing
             */
            String[] parse = dt.get(4).split("[T]");
            LocalDate localDate = LocalDate.parse(parse[0]);
            String[] parse1 = parse[1].split("[+]");
            LocalTime localTime = LocalTime.parse(parse1[0]);
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, localTime, zoneId);

            /**
             * Nested Coordinates parsing
             */
            long x=0;
            try{
                x = Long.parseLong(dt.get(2));
            }catch (NumberFormatException exception){
            }
            long y=0;
            try{
                y=Long.parseLong(dt.get(3));
            }catch (NumberFormatException exception){
            }
            Coordinates coordinates = new Coordinates(x,y);

            /**
             * Nested Person Nested Location parsing
             */
            Location location = null;
            long lx = 0;
            try{
                lx = Long.parseLong(dt.get(13));
            }catch (NumberFormatException exception){
            }
            double ly = 0;
            try{
                ly = Double.parseDouble(dt.get(14));
            }catch (NumberFormatException exception){
            }

            location = new Location(lx, ly, dt.get(15));


            /**
             * Nested Person parsing
             */
            double pW=0;
            try{
                pW = Double.parseDouble(dt.get(10));
            }catch (NumberFormatException exception){
            }
            Person person = null;
            if(dt.get(12).equals("")&&!dt.get(11).equals(""))
                person = new Person(dt.get(9),pW,Color.valueOf(dt.get(11)),null,location);
            if(dt.get(11).equals("")&&dt.get(12).equals(""))
                person = new Person(dt.get(9),pW,null,null,location);
            else if (!dt.get(12).equals("")){
                 person = new Person(dt.get(9), pW, Color.valueOf(dt.get(11)),
                        Country.valueOf(dt.get(12)), location);
            }

            /**
             * StudyGroup parsing
             */
            long sC=0;
            try{
                sC = Long.parseLong(dt.get(5));
            }catch (NumberFormatException exception){
            }

            long eS =0;
            try{
                eS = Long.parseLong(dt.get(6));
            }catch (NumberFormatException exception){
            }
            if(!dt.get(7).equals("")&&!dt.get(8).equals("")) {
                studyGroup = new StudyGroup(dt.get(1), coordinates, zonedDateTime, sC,
                        eS, FormOfEducation.valueOf(dt.get(7)), Semester.valueOf(dt.get(8)), person);
                studyGroup.setId(id);
                return studyGroup;
            }if(dt.get(7).equals("")&&!dt.get(8).equals("")) {
                studyGroup = new StudyGroup(dt.get(1), coordinates, zonedDateTime, sC,
                        eS, null, Semester.valueOf(dt.get(8)), person);
                studyGroup.setId(id);
                return studyGroup;
            }
             if(dt.get(8).equals("")&&!dt.get(7).equals("")) {
                 studyGroup = new StudyGroup(dt.get(1), coordinates, zonedDateTime, sC,
                         eS, FormOfEducation.valueOf(dt.get(7)), null, person);
                 studyGroup.setId(id);
                 return studyGroup;
             }
             else if(dt.get(7).equals("")&&dt.get(8).equals("")) {
                 studyGroup = new StudyGroup(dt.get(1), coordinates, zonedDateTime, sC,
                         eS, null, null, person);
                 studyGroup.setId(id);
                 return studyGroup;
             }else{
                 return null;
             }
        }catch (IndexOutOfBoundsException exception){
            throw new DataParseException(ConsoleColor.RED_BACKGROUND+" :("+ConsoleColor.RESET+"\n");
        }catch(DateTimeParseException exception){
            throw new DataParseException(ConsoleColor.RED_BACKGROUND+" Invalid date :("+ConsoleColor.RESET+"\n");
        }
    }
}


