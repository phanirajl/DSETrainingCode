CREATE KEYSPACE IF NOT EXISTS bootcamp WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};


CREATE TABLE IF NOT EXISTS user_address_multiple_secondary_index (
    unique          timeuuid,
    first_name      text,
    last_name       text,
    mid_name        text,
    ordinal         text,
    birthday        date,
    occupation      text,
    title           text,
    address_name    text,
    address1        text,
    address2        text,
    address3        text,
    city            text,
    state           text,
    zip             text,
    country         text,
    PRIMARY KEY ((unique), address_name) 
) WITH comment = 'Single table with user_address, now multiple address by cc, and each field can change independantly. Not good for high cardianliry fields, only exact matches still)';

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ef510-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1387 Tridelphia Place', 'Apt #100160', '', '2001-03-16', 'Alma', 'USA', 'Janet', 'Abbott', 'Steven', 'Unemployeed', '', 'LA', '', '23674' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2054a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '818 Senator Circle', '', '', '1954-07-11', 'West Point', 'USA', 'Larry', 'Walker', 'Kyle', 'Architect', '', 'CA', 'IV', '46229' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2054a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '1419 Wayne Street', 'Apt #100620', '', '1950-05-18', 'Brinson', 'USA', 'Patrick', 'Walker', 'Kaylee', 'Architect', '', 'CO', 'VP', '66887' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2117f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1495 Salgarber Ave', 'Apt #100876', '', '1949-01-06', 'Lithonia', 'USA', 'Brad', 'Mays', 'Greg', 'Maid', '', 'AZ', 'Analyst', '86957' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2117f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SHIPPING', '1736 Berkley Road', '', '', '1949-01-06', 'Hampton', 'USA', 'Brad', 'Mays', 'Greg', 'Maid', '', 'NE', 'Analyst', '86132' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c222960-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '772 Ryan Path', '', '', '1982-10-06', 'Suwanee', 'USA', 'Debbie', 'Greene', 'Sara', 'Janitor', '', 'DE', 'Level III', '11699' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c222960-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '434 Cleveland Street', '', '', '1982-10-06', 'Stillwell', 'USA', 'Debbie', 'Greene', 'Sara', 'Janitor', '', 'ID', 'Level III', '27657' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225071-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '1009 Hicks Ave', '', 'Apt #100311', '1909-11-20', 'Bainbridge', 'USA', 'Richard', 'Richmond', 'Herbert', 'Butler', 'I', 'AL', 'Chief', '67207' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225071-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1758 Stanton Boulevard', '', '', '1909-11-20', 'Culloden', 'USA', 'Richard', 'Richmond', 'Herbert', 'Butler', 'I', 'AR', 'Chief', '67335' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'DAUGHTERS', '1637 Lincolnway Square', 'Suite #100765', '', '1995-11-01', 'Carnesville', 'USA', 'Jenny', 'McGuire', 'Dana', 'Student', '', 'WV', '', '30255' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '473 Briarbush Ave', '', '', '1993-08-03', 'Weber', 'USA', 'Thomas', 'McGuire', 'Tim', 'Student', 'II', 'OR', '', '60946' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '567 Ledbetter Trail', '', '', '1974-03-01', 'Eulonia', 'USA', 'Janice', 'McGuire', 'Jill', 'Human Resources', '', 'MS', 'Director', '34593' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1004 Waters Station', 'Apt #100478', '', '1973-11-01', 'Baxley', 'USA', 'Tim', 'McGuire', 'Thomas', 'Architect', '', 'AL', 'Chief', '25221' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'DAUGHTERS', '1217 Carbondale Ave', '', '', '1992-08-01', 'Wray', 'USA', 'Rocky', 'Bender', 'Tam', 'Sales', '', 'CT', 'VP', '36292' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '505 Humphrey Drv', '', '', '1992-08-01', 'Sunsweet', 'USA', 'Rocky', 'Bender', 'Tam', 'Sales', '', 'HI', 'VP', '77562-7805' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2361e0-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '933 Moxahala Place', '', '', '1928-07-14', 'Augusta', 'USA', 'Edward', 'Campbell', 'Bill', 'Nurse', 'II', 'KS', 'Level V', '72080' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2361e0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '662 Powell Circle', 'Suite #100932', '', '1928-07-14', 'Covington', 'USA', 'Edward', 'Campbell', 'Bill', 'Nurse', 'II', 'HI', 'Level V', '18685' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2361e0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1376 Ayers Drv', '', '', '1928-07-14', 'Ringgold', 'USA', 'Edward', 'Campbell', 'Bill', 'Nurse', 'II', 'AL', 'Level V', '14252' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20a2c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1104 Carlysle Lane', 'Apt #100793', '', '1945-03-07', 'Centerville', 'USA', 'Carrie', 'McDowell', 'Brenda', 'Doctor', 'Sr', 'MS', 'CEO', '68771' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20a2c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1297 Kimes Way', 'Suite #100830', '', '1945-03-07', 'Abba', 'USA', 'Carrie', 'McDowell', 'Brenda', 'Doctor', 'Sr', 'OK', 'CEO', '32783' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20a2c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1784 Spielbusch Run', 'Apt #100112', '', '1945-03-07', 'Pineora', 'USA', 'Carrie', 'McDowell', 'Brenda', 'Doctor', 'Sr', 'PR', 'CEO', '43998' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '931 Dana Blvd', '', '', '1957-06-19', 'Saint George', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'MA', 'Senior', '50345' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'MOTHERS', '999 Ward Ridge', '', '', '1957-06-19', 'Enigma', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'VT', 'Senior', '21683' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '694 Heber Place', '', '', '1957-06-19', 'Sunsweet', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'ID', 'Senior', '23413' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1147 Homeless Court', 'Apt #10089', '', '1957-06-19', 'Rebecca', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'OH', 'Senior', '42596-8790' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21b430-ff7c-11e6-9008-a1d8a5a6f0cc, 'FATHERS', '1593 Swingle Square', 'Apt #100297', '', '1929-07-12', 'Ludowici', 'USA', 'Barb', 'Maddox', 'Chelsea', 'Business', 'I', 'IN', 'Lead', '60595' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21b430-ff7c-11e6-9008-a1d8a5a6f0cc, 'MOTHERS', '809 Brown Court', 'Suite #100723', '', '1929-07-12', 'Cuthbert', 'USA', 'Barb', 'Maddox', 'Chelsea', 'Business', 'I', 'CT', 'Lead', '97685' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21b430-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '984 Nottingham Rd', '', '', '1929-07-12', 'Dock Junction', 'USA', 'Barb', 'Maddox', 'Chelsea', 'Business', 'I', 'ND', 'Lead', '50897' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ece00-ff7c-11e6-9008-a1d8a5a6f0cc, 'GRANDPARENTS', '1038 Merlin Boulevard', 'Apt #100894', '', '1987-06-14', 'Alapaha', 'USA', 'Stephanie', 'Fry', 'Antonio', 'Sales', 'Sir', 'TN', 'Lead', '22264' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f4330-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '1546 Woodbrook Run', 'Apt #100144', '', '1953-11-22', 'Stone Mountain', 'USA', 'Brian', 'Jefferson', 'Dusty', 'Marine', 'Sr', 'GA', '', '19501' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f4330-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '937 Jonathan Drv', '', '', '1953-11-22', 'Rutledge', 'USA', 'Brian', 'Jefferson', 'Dusty', 'Marine', 'Sr', 'KY', '', '34613' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f4330-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1485 Hickam St', 'Suite #100501', '', '1953-11-22', 'Soperton', 'USA', 'Brian', 'Jefferson', 'Dusty', 'Marine', 'Sr', 'MT', '', '59697' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c19ec00-ff7c-11e6-9008-a1d8a5a6f0cc, 'FATHERS', '828 Arch Avenue', '', '', '1907-09-28', 'Soperton', 'USA', 'Lindsey', 'Craft', 'Erica', 'Human Resources', 'II', 'TN', 'Analyst', '61211' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c19ec00-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1552 Lashley Drive', 'Suite #100771', '', '1907-09-28', 'Kennesaw', 'USA', 'Lindsey', 'Craft', 'Erica', 'Human Resources', 'II', 'GA', 'Analyst', '88109' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ea6f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1567 Ballov Drive', '', '', '1964-09-14', 'Jeffersonville', 'USA', 'Nancy', 'Stevens', 'Chasity', 'Finance', '', 'NV', 'VP', '51222' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ea6f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '425 Robinwood Place', '', '', '1964-09-14', 'Colesburg', 'USA', 'Nancy', 'Stevens', 'Chasity', 'Finance', '', 'UT', 'VP', '12799' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ea6f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1166 Adamsville Lane', 'Apt #100756', '', '1964-09-14', 'Cobbtown', 'USA', 'Nancy', 'Stevens', 'Chasity', 'Finance', '', 'VA', 'VP', '82623' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c229e90-ff7c-11e6-9008-a1d8a5a6f0cc, 'DAUGHTERS', '636 Shasta Crescent', 'Apt #10022', '', '1943-12-23', 'Lovejoy', 'USA', 'Justin', 'Henry', 'Juanita', 'Astronaut', 'II', 'OH', 'Dr', '21628' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c229e90-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '517 Rhonda Road', '', '', '1943-12-23', 'Blackshear', 'USA', 'Justin', 'Henry', 'Juanita', 'Astronaut', 'II', 'TX', 'Dr', '85221' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c202d90-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1113 Willow Circle', '', '', '1919-09-19', 'Sandersville', 'USA', 'Kylie', 'Adams', 'Gabriel', 'Human Resources', '', 'WI', 'Mam', '89988' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2388f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '494 Stevens Park', '', '', '1974-11-21', 'Toomsboro', 'USA', 'Sue', 'Ferrell', 'Andy', 'Sales', 'III', 'IA', 'Dr', '14756' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2388f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1586 Sycamore Ave', '', '', '1974-11-21', 'Montrose', 'USA', 'Sue', 'Ferrell', 'Andy', 'Sales', 'III', 'MT', 'Dr', '68528' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2388f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WORK', '434 Mackenzie Path', 'Suite #100659', '', '1974-11-21', 'Homer', 'USA', 'Sue', 'Ferrell', 'Andy', 'Sales', 'III', 'WA', 'Dr', '95271' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c23b000-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '1525 Lancaster-Chillicoth Drive', 'Suite #100555', '', '1916-09-14', 'Ray City', 'USA', 'Jessie', 'Cortez', 'Dusty', 'Pilot', '', 'GA', 'Level I', '78780' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c23b000-ff7c-11e6-9008-a1d8a5a6f0cc, 'WORK', '1056 Stansberry Terrace', '', '', '1916-09-14', 'Darien', 'USA', 'Jessie', 'Cortez', 'Dusty', 'Pilot', '', 'FL', 'Level I', '45996' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f6a40-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1291 Wolford Drive', '', '', '2010-11-10', 'Thomson', 'USA', 'Marty', 'Franco', 'Sam', 'Homemaker', '', 'CA', 'Head', '27817' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f6a40-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '527 Deerfield Drive', 'Apt #100674', '', '2010-11-10', 'Buford', 'USA', 'Marty', 'Franco', 'Sam', 'Homemaker', '', 'NE', 'Head', '76099' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c207bb0-ff7c-11e6-9008-a1d8a5a6f0cc, 'GRANDPARENTS', '1642 Fess Crescent', '', '', '1929-11-02', 'Hinesville', 'USA', 'Erin', 'Stafford', 'Leroy', 'Marine', '', 'IN', 'Level V', '88743' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c207bb0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '746 Gallia Avenue', 'Apt #100987', '', '1929-11-02', 'Queensland', 'USA', 'Erin', 'Stafford', 'Leroy', 'Marine', '', 'OR', 'Level V', '16294' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c207bb0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '571 Marne Lane', 'Suite #100514', 'Suite #100984', '1929-11-02', 'Bowens Mill', 'USA', 'Erin', 'Stafford', 'Leroy', 'Marine', '', 'MS', 'Level V', '23800' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a1-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '574 Seaman Heights', 'Suite #100818', '', '1982-09-03', 'Camilla', 'USA', 'Ronda', 'Hopper', 'Lance', 'Education', '', 'PA', 'Level IV', '50184' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a1-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '1280 Keen Way', 'Suite #100753', '', '1982-09-03', 'Douglasville', 'USA', 'Ronda', 'Hopper', 'Lance', 'Education', '', 'WA', 'Level IV', '71098' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20c9d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'EXS', '763 Talley Circle', 'Suite #100149', 'Apt #100422', '1961-05-26', 'Smithville', 'USA', 'Jeremiah', 'Bird', 'Kris', 'Spy', '', 'VA', 'Head', '90070' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20c9d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1420 Mcarthur Park', 'Suite #10089', '', '1961-05-26', 'Mcrae', 'USA', 'Jeremiah', 'Bird', 'Kris', 'Spy', '', 'NV', 'Head', '75592' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20c9d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1320 Sunflower Trail', '', '', '1961-05-26', 'Auburn', 'USA', 'Jeremiah', 'Bird', 'Kris', 'Spy', '', 'VT', 'Head', '15238' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21db40-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '1711 Scenic Park', 'Suite #10074', '', '1909-12-31', 'Griffin', 'USA', 'Douglas', 'Shelton', 'Patty', 'IT', 'III', 'NH', 'Chief', '65122' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21db40-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '517 Frame Crescent', 'Apt #100190', '', '1909-12-31', 'Colesburg', 'USA', 'Douglas', 'Shelton', 'Patty', 'IT', 'III', 'NM', 'Chief', '17464' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f9150-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1643 Dietz Street', 'Apt #100526', '', '1924-05-13', 'Forsyth', 'USA', 'Myron', 'Blanchard', 'Rod', 'Chef', '', 'RI', 'Head', '30016' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f9150-ff7c-11e6-9008-a1d8a5a6f0cc, 'WORK', '1217 Sofin Street', '', '', '1924-05-13', 'Athens', 'USA', 'Myron', 'Blanchard', 'Rod', 'Chef', '', 'UT', 'Head', '30185' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c213f00-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '1176 Sheila Ln', '', '', '1950-03-03', 'Sylvania', 'USA', 'Tristan', 'Curry', 'Jade', 'Finance', '', 'NC', 'Dr', '18172' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c213f00-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1288 Milton Rd', '', '', '1950-03-03', 'Kinderlou', 'USA', 'Tristan', 'Curry', 'Jade', 'Finance', '', 'MA', 'Dr', '43762' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c213f00-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '886 Beech Station', 'Suite #100619', '', '1950-03-03', 'Alapaha', 'USA', 'Tristan', 'Curry', 'Jade', 'Finance', '', 'ID', 'Dr', '95739' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'EXS', '790 Cemetery Circle', '', '', '1941-07-19', 'Axson', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'NC', 'Senior', '19445-2314' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1767 Blackburn Street', '', '', '1941-07-19', 'Lake City', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'CT', 'Senior', '35780' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '993 Ridgefield Path', 'Apt #100385', '', '1941-07-19', 'Claxton', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'TX', 'Senior', '60020' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1665 Kevrob Ave', '', '', '1941-07-19', 'Thalman', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'MA', 'Senior', '66172' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225070-ff7c-11e6-9008-a1d8a5a6f0cc, 'EXS', '832 Cooperriders St', '', '', '1922-02-04', 'Hopeulikit', 'USA', 'Ruth', 'Kinney', 'Craig', 'Spy', '', 'GU', 'Director', '85914' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225070-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1475 Taylor Street', 'Apt #100345', '', '1922-02-04', 'Lookout Mountain', 'USA', 'Ruth', 'Kinney', 'Craig', 'Spy', '', 'WA', 'Director', '98578' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'GRANDPARENTS', '772 Evergreen St', '', '', '1905-03-03', 'Baden', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'DE', 'Analyst', '86686' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '1789 Amsterdam Way', '', '', '1905-03-03', 'Rockmart', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'IN', 'Analyst', '14557' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '877 Green Rd', '', '', '1905-03-03', 'Lawrenceville', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'TX', 'Analyst', '30221' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '686 Talford Station', '', '', '1905-03-03', 'Mcrae', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'OH', 'Analyst', '59657' );

INSERT INTO user_address_multiple_secondary_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1fb860-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '808 Ashley Ave', '', 'Suite #100914', '1961-09-28', 'Morven', 'USA', 'Michael', 'Gross', 'Richard', 'Marine', '', 'DE', 'Sir', '54212' );

SELECT COUNT(*) FROM user_address_multiple_secondary_index;

CREATE INDEX IF NOT EXISTS user_address_last_name_secondary_idx ON user_address_multiple_secondary_index(last_name);

SELECT * FROM user_address_multiple_secondary_index WHERE last_name = 'MCGuire';

SELECT * FROM user_address_multiple_secondary_index WHERE last_name = 'McGuire';

CREATE MATERIALIZED VIEW IF NOT EXISTS user_address_multiple_materialized_view_by_last AS
    SELECT unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name,
    mid_name, occupation, ordinal, state, title, zip
    FROM user_address_multiple
    WHERE last_name IS NOT NULL AND unique IS NOT NULL AND address_name IS NOT NULL
    PRIMARY KEY ( (last_name), unique, address_name );
    
SELECT * FROM user_address_multiple_materialized_view_by_last WHERE last_name = 'McGuire';

CREATE TABLE IF NOT EXISTS user_address_multiple_sasi_index (
    unique          timeuuid,
    first_name      text,
    last_name       text,
    mid_name        text,
    ordinal         text,
    birthday        date,
    occupation      text,
    title           text,
    address_name    text,
    address1        text,
    address2        text,
    address3        text,
    city            text,
    state           text,
    zip             text,
    country         text,
    PRIMARY KEY ((unique), address_name) 
) WITH comment = 'Single table with user_address, now multiple address by cc, and each field can change independantly. But SASI index will now be applied to last name, can wildcard search, and case insensitve)';

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ef510-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1387 Tridelphia Place', 'Apt #100160', '', '2001-03-16', 'Alma', 'USA', 'Janet', 'Abbott', 'Steven', 'Unemployeed', '', 'LA', '', '23674' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2054a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '818 Senator Circle', '', '', '1954-07-11', 'West Point', 'USA', 'Larry', 'Walker', 'Kyle', 'Architect', '', 'CA', 'IV', '46229' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2054a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '1419 Wayne Street', 'Apt #100620', '', '1950-05-18', 'Brinson', 'USA', 'Patrick', 'Walker', 'Kaylee', 'Architect', '', 'CO', 'VP', '66887' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2117f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1495 Salgarber Ave', 'Apt #100876', '', '1949-01-06', 'Lithonia', 'USA', 'Brad', 'Mays', 'Greg', 'Maid', '', 'AZ', 'Analyst', '86957' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2117f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SHIPPING', '1736 Berkley Road', '', '', '1949-01-06', 'Hampton', 'USA', 'Brad', 'Mays', 'Greg', 'Maid', '', 'NE', 'Analyst', '86132' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c222960-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '772 Ryan Path', '', '', '1982-10-06', 'Suwanee', 'USA', 'Debbie', 'Greene', 'Sara', 'Janitor', '', 'DE', 'Level III', '11699' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c222960-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '434 Cleveland Street', '', '', '1982-10-06', 'Stillwell', 'USA', 'Debbie', 'Greene', 'Sara', 'Janitor', '', 'ID', 'Level III', '27657' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225071-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '1009 Hicks Ave', '', 'Apt #100311', '1909-11-20', 'Bainbridge', 'USA', 'Richard', 'Richmond', 'Herbert', 'Butler', 'I', 'AL', 'Chief', '67207' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225071-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1758 Stanton Boulevard', '', '', '1909-11-20', 'Culloden', 'USA', 'Richard', 'Richmond', 'Herbert', 'Butler', 'I', 'AR', 'Chief', '67335' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'DAUGHTERS', '1637 Lincolnway Square', 'Suite #100765', '', '1995-11-01', 'Carnesville', 'USA', 'Jenny', 'McGuire', 'Dana', 'Student', '', 'WV', '', '30255' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '473 Briarbush Ave', '', '', '1993-08-03', 'Weber', 'USA', 'Thomas', 'McGuire', 'Tim', 'Student', 'II', 'OR', '', '60946' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '567 Ledbetter Trail', '', '', '1974-03-01', 'Eulonia', 'USA', 'Janice', 'McGuire', 'Jill', 'Human Resources', '', 'MS', 'Director', '34593' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1004 Waters Station', 'Apt #100478', '', '1973-11-01', 'Baxley', 'USA', 'Tim', 'McGuire', 'Thomas', 'Architect', '', 'AL', 'Chief', '25221' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'DAUGHTERS', '1217 Carbondale Ave', '', '', '1992-08-01', 'Wray', 'USA', 'Rocky', 'Bender', 'Tam', 'Sales', '', 'CT', 'VP', '36292' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '505 Humphrey Drv', '', '', '1992-08-01', 'Sunsweet', 'USA', 'Rocky', 'Bender', 'Tam', 'Sales', '', 'HI', 'VP', '77562-7805' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2361e0-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '933 Moxahala Place', '', '', '1928-07-14', 'Augusta', 'USA', 'Edward', 'Campbell', 'Bill', 'Nurse', 'II', 'KS', 'Level V', '72080' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2361e0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '662 Powell Circle', 'Suite #100932', '', '1928-07-14', 'Covington', 'USA', 'Edward', 'Campbell', 'Bill', 'Nurse', 'II', 'HI', 'Level V', '18685' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2361e0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1376 Ayers Drv', '', '', '1928-07-14', 'Ringgold', 'USA', 'Edward', 'Campbell', 'Bill', 'Nurse', 'II', 'AL', 'Level V', '14252' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20a2c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1104 Carlysle Lane', 'Apt #100793', '', '1945-03-07', 'Centerville', 'USA', 'Carrie', 'McDowell', 'Brenda', 'Doctor', 'Sr', 'MS', 'CEO', '68771' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20a2c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1297 Kimes Way', 'Suite #100830', '', '1945-03-07', 'Abba', 'USA', 'Carrie', 'McDowell', 'Brenda', 'Doctor', 'Sr', 'OK', 'CEO', '32783' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20a2c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1784 Spielbusch Run', 'Apt #100112', '', '1945-03-07', 'Pineora', 'USA', 'Carrie', 'McDowell', 'Brenda', 'Doctor', 'Sr', 'PR', 'CEO', '43998' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '931 Dana Blvd', '', '', '1957-06-19', 'Saint George', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'MA', 'Senior', '50345' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'MOTHERS', '999 Ward Ridge', '', '', '1957-06-19', 'Enigma', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'VT', 'Senior', '21683' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '694 Heber Place', '', '', '1957-06-19', 'Sunsweet', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'ID', 'Senior', '23413' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1147 Homeless Court', 'Apt #10089', '', '1957-06-19', 'Rebecca', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'OH', 'Senior', '42596-8790' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21b430-ff7c-11e6-9008-a1d8a5a6f0cc, 'FATHERS', '1593 Swingle Square', 'Apt #100297', '', '1929-07-12', 'Ludowici', 'USA', 'Barb', 'Maddox', 'Chelsea', 'Business', 'I', 'IN', 'Lead', '60595' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21b430-ff7c-11e6-9008-a1d8a5a6f0cc, 'MOTHERS', '809 Brown Court', 'Suite #100723', '', '1929-07-12', 'Cuthbert', 'USA', 'Barb', 'Maddox', 'Chelsea', 'Business', 'I', 'CT', 'Lead', '97685' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21b430-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '984 Nottingham Rd', '', '', '1929-07-12', 'Dock Junction', 'USA', 'Barb', 'Maddox', 'Chelsea', 'Business', 'I', 'ND', 'Lead', '50897' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ece00-ff7c-11e6-9008-a1d8a5a6f0cc, 'GRANDPARENTS', '1038 Merlin Boulevard', 'Apt #100894', '', '1987-06-14', 'Alapaha', 'USA', 'Stephanie', 'Fry', 'Antonio', 'Sales', 'Sir', 'TN', 'Lead', '22264' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f4330-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '1546 Woodbrook Run', 'Apt #100144', '', '1953-11-22', 'Stone Mountain', 'USA', 'Brian', 'Jefferson', 'Dusty', 'Marine', 'Sr', 'GA', '', '19501' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f4330-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '937 Jonathan Drv', '', '', '1953-11-22', 'Rutledge', 'USA', 'Brian', 'Jefferson', 'Dusty', 'Marine', 'Sr', 'KY', '', '34613' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f4330-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1485 Hickam St', 'Suite #100501', '', '1953-11-22', 'Soperton', 'USA', 'Brian', 'Jefferson', 'Dusty', 'Marine', 'Sr', 'MT', '', '59697' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c19ec00-ff7c-11e6-9008-a1d8a5a6f0cc, 'FATHERS', '828 Arch Avenue', '', '', '1907-09-28', 'Soperton', 'USA', 'Lindsey', 'Craft', 'Erica', 'Human Resources', 'II', 'TN', 'Analyst', '61211' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c19ec00-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1552 Lashley Drive', 'Suite #100771', '', '1907-09-28', 'Kennesaw', 'USA', 'Lindsey', 'Craft', 'Erica', 'Human Resources', 'II', 'GA', 'Analyst', '88109' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ea6f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1567 Ballov Drive', '', '', '1964-09-14', 'Jeffersonville', 'USA', 'Nancy', 'Stevens', 'Chasity', 'Finance', '', 'NV', 'VP', '51222' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ea6f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '425 Robinwood Place', '', '', '1964-09-14', 'Colesburg', 'USA', 'Nancy', 'Stevens', 'Chasity', 'Finance', '', 'UT', 'VP', '12799' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ea6f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1166 Adamsville Lane', 'Apt #100756', '', '1964-09-14', 'Cobbtown', 'USA', 'Nancy', 'Stevens', 'Chasity', 'Finance', '', 'VA', 'VP', '82623' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c229e90-ff7c-11e6-9008-a1d8a5a6f0cc, 'DAUGHTERS', '636 Shasta Crescent', 'Apt #10022', '', '1943-12-23', 'Lovejoy', 'USA', 'Justin', 'Henry', 'Juanita', 'Astronaut', 'II', 'OH', 'Dr', '21628' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c229e90-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '517 Rhonda Road', '', '', '1943-12-23', 'Blackshear', 'USA', 'Justin', 'Henry', 'Juanita', 'Astronaut', 'II', 'TX', 'Dr', '85221' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c202d90-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1113 Willow Circle', '', '', '1919-09-19', 'Sandersville', 'USA', 'Kylie', 'Adams', 'Gabriel', 'Human Resources', '', 'WI', 'Mam', '89988' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2388f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '494 Stevens Park', '', '', '1974-11-21', 'Toomsboro', 'USA', 'Sue', 'Ferrell', 'Andy', 'Sales', 'III', 'IA', 'Dr', '14756' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2388f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1586 Sycamore Ave', '', '', '1974-11-21', 'Montrose', 'USA', 'Sue', 'Ferrell', 'Andy', 'Sales', 'III', 'MT', 'Dr', '68528' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2388f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WORK', '434 Mackenzie Path', 'Suite #100659', '', '1974-11-21', 'Homer', 'USA', 'Sue', 'Ferrell', 'Andy', 'Sales', 'III', 'WA', 'Dr', '95271' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c23b000-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '1525 Lancaster-Chillicoth Drive', 'Suite #100555', '', '1916-09-14', 'Ray City', 'USA', 'Jessie', 'Cortez', 'Dusty', 'Pilot', '', 'GA', 'Level I', '78780' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c23b000-ff7c-11e6-9008-a1d8a5a6f0cc, 'WORK', '1056 Stansberry Terrace', '', '', '1916-09-14', 'Darien', 'USA', 'Jessie', 'Cortez', 'Dusty', 'Pilot', '', 'FL', 'Level I', '45996' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f6a40-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1291 Wolford Drive', '', '', '2010-11-10', 'Thomson', 'USA', 'Marty', 'Franco', 'Sam', 'Homemaker', '', 'CA', 'Head', '27817' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f6a40-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '527 Deerfield Drive', 'Apt #100674', '', '2010-11-10', 'Buford', 'USA', 'Marty', 'Franco', 'Sam', 'Homemaker', '', 'NE', 'Head', '76099' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c207bb0-ff7c-11e6-9008-a1d8a5a6f0cc, 'GRANDPARENTS', '1642 Fess Crescent', '', '', '1929-11-02', 'Hinesville', 'USA', 'Erin', 'Stafford', 'Leroy', 'Marine', '', 'IN', 'Level V', '88743' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c207bb0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '746 Gallia Avenue', 'Apt #100987', '', '1929-11-02', 'Queensland', 'USA', 'Erin', 'Stafford', 'Leroy', 'Marine', '', 'OR', 'Level V', '16294' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c207bb0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '571 Marne Lane', 'Suite #100514', 'Suite #100984', '1929-11-02', 'Bowens Mill', 'USA', 'Erin', 'Stafford', 'Leroy', 'Marine', '', 'MS', 'Level V', '23800' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a1-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '574 Seaman Heights', 'Suite #100818', '', '1982-09-03', 'Camilla', 'USA', 'Ronda', 'Hopper', 'Lance', 'Education', '', 'PA', 'Level IV', '50184' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a1-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '1280 Keen Way', 'Suite #100753', '', '1982-09-03', 'Douglasville', 'USA', 'Ronda', 'Hopper', 'Lance', 'Education', '', 'WA', 'Level IV', '71098' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20c9d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'EXS', '763 Talley Circle', 'Suite #100149', 'Apt #100422', '1961-05-26', 'Smithville', 'USA', 'Jeremiah', 'Bird', 'Kris', 'Spy', '', 'VA', 'Head', '90070' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20c9d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1420 Mcarthur Park', 'Suite #10089', '', '1961-05-26', 'Mcrae', 'USA', 'Jeremiah', 'Bird', 'Kris', 'Spy', '', 'NV', 'Head', '75592' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20c9d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1320 Sunflower Trail', '', '', '1961-05-26', 'Auburn', 'USA', 'Jeremiah', 'Bird', 'Kris', 'Spy', '', 'VT', 'Head', '15238' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21db40-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '1711 Scenic Park', 'Suite #10074', '', '1909-12-31', 'Griffin', 'USA', 'Douglas', 'Shelton', 'Patty', 'IT', 'III', 'NH', 'Chief', '65122' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21db40-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '517 Frame Crescent', 'Apt #100190', '', '1909-12-31', 'Colesburg', 'USA', 'Douglas', 'Shelton', 'Patty', 'IT', 'III', 'NM', 'Chief', '17464' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f9150-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1643 Dietz Street', 'Apt #100526', '', '1924-05-13', 'Forsyth', 'USA', 'Myron', 'Blanchard', 'Rod', 'Chef', '', 'RI', 'Head', '30016' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f9150-ff7c-11e6-9008-a1d8a5a6f0cc, 'WORK', '1217 Sofin Street', '', '', '1924-05-13', 'Athens', 'USA', 'Myron', 'Blanchard', 'Rod', 'Chef', '', 'UT', 'Head', '30185' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c213f00-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '1176 Sheila Ln', '', '', '1950-03-03', 'Sylvania', 'USA', 'Tristan', 'Curry', 'Jade', 'Finance', '', 'NC', 'Dr', '18172' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c213f00-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1288 Milton Rd', '', '', '1950-03-03', 'Kinderlou', 'USA', 'Tristan', 'Curry', 'Jade', 'Finance', '', 'MA', 'Dr', '43762' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c213f00-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '886 Beech Station', 'Suite #100619', '', '1950-03-03', 'Alapaha', 'USA', 'Tristan', 'Curry', 'Jade', 'Finance', '', 'ID', 'Dr', '95739' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'EXS', '790 Cemetery Circle', '', '', '1941-07-19', 'Axson', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'NC', 'Senior', '19445-2314' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1767 Blackburn Street', '', '', '1941-07-19', 'Lake City', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'CT', 'Senior', '35780' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '993 Ridgefield Path', 'Apt #100385', '', '1941-07-19', 'Claxton', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'TX', 'Senior', '60020' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1665 Kevrob Ave', '', '', '1941-07-19', 'Thalman', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'MA', 'Senior', '66172' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225070-ff7c-11e6-9008-a1d8a5a6f0cc, 'EXS', '832 Cooperriders St', '', '', '1922-02-04', 'Hopeulikit', 'USA', 'Ruth', 'Kinney', 'Craig', 'Spy', '', 'GU', 'Director', '85914' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225070-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1475 Taylor Street', 'Apt #100345', '', '1922-02-04', 'Lookout Mountain', 'USA', 'Ruth', 'Kinney', 'Craig', 'Spy', '', 'WA', 'Director', '98578' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'GRANDPARENTS', '772 Evergreen St', '', '', '1905-03-03', 'Baden', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'DE', 'Analyst', '86686' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '1789 Amsterdam Way', '', '', '1905-03-03', 'Rockmart', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'IN', 'Analyst', '14557' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '877 Green Rd', '', '', '1905-03-03', 'Lawrenceville', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'TX', 'Analyst', '30221' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '686 Talford Station', '', '', '1905-03-03', 'Mcrae', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'OH', 'Analyst', '59657' );

INSERT INTO user_address_multiple_sasi_index ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1fb860-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '808 Ashley Ave', '', 'Suite #100914', '1961-09-28', 'Morven', 'USA', 'Michael', 'Gross', 'Richard', 'Marine', '', 'DE', 'Sir', '54212' );

SELECT COUNT(*) FROM user_address_multiple_sasi_index;

CREATE CUSTOM INDEX IF NOT EXISTS user_address_last_name_sasi_idx ON user_address_multiple_sasi_index (last_name) 
  USING 'org.apache.cassandra.index.sasi.SASIIndex'
  WITH OPTIONS = {
    'mode': 'CONTAINS',
    'analyzer_class': 'org.apache.cassandra.index.sasi.analyzer.StandardAnalyzer',
    'analyzed': 'true',
    'tokenization_skip_stop_words': 'and, the, or',
    'tokenization_enable_stemming': 'true',
    'tokenization_normalize_lowercase': 'true',
    'tokenization_locale': 'en'
};

SELECT * FROM user_address_multiple_sasi_index WHERE last_name LIKE '%m%';

CREATE TABLE IF NOT EXISTS user_address_multiple_solr (
    unique          timeuuid,
    first_name      text,
    last_name       text,
    mid_name        text,
    ordinal         text,
    birthday        date,
    occupation      text,
    title           text,
    address_name    text,
    address1        text,
    address2        text,
    address3        text,
    city            text,
    state           text,
    zip             text,
    country         text,
    PRIMARY KEY ((unique), address_name) 
) WITH comment = 'Single table with user_address, now multiple address by cc, and each field can change independantly. SOLR is 10 times slower that partion search, but gives more freedom)';


INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ef510-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1387 Tridelphia Place', 'Apt #100160', '', '2001-03-16', 'Alma', 'USA', 'Janet', 'Abbott', 'Steven', 'Unemployeed', '', 'LA', '', '23674' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2054a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '818 Senator Circle', '', '', '1954-07-11', 'West Point', 'USA', 'Larry', 'Walker', 'Kyle', 'Architect', '', 'CA', 'IV', '46229' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2054a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '1419 Wayne Street', 'Apt #100620', '', '1950-05-18', 'Brinson', 'USA', 'Patrick', 'Walker', 'Kaylee', 'Architect', '', 'CO', 'VP', '66887' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2117f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1495 Salgarber Ave', 'Apt #100876', '', '1949-01-06', 'Lithonia', 'USA', 'Brad', 'Mays', 'Greg', 'Maid', '', 'AZ', 'Analyst', '86957' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2117f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SHIPPING', '1736 Berkley Road', '', '', '1949-01-06', 'Hampton', 'USA', 'Brad', 'Mays', 'Greg', 'Maid', '', 'NE', 'Analyst', '86132' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c222960-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '772 Ryan Path', '', '', '1982-10-06', 'Suwanee', 'USA', 'Debbie', 'Greene', 'Sara', 'Janitor', '', 'DE', 'Level III', '11699' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c222960-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '434 Cleveland Street', '', '', '1982-10-06', 'Stillwell', 'USA', 'Debbie', 'Greene', 'Sara', 'Janitor', '', 'ID', 'Level III', '27657' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225071-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '1009 Hicks Ave', '', 'Apt #100311', '1909-11-20', 'Bainbridge', 'USA', 'Richard', 'Richmond', 'Herbert', 'Butler', 'I', 'AL', 'Chief', '67207' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225071-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1758 Stanton Boulevard', '', '', '1909-11-20', 'Culloden', 'USA', 'Richard', 'Richmond', 'Herbert', 'Butler', 'I', 'AR', 'Chief', '67335' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'DAUGHTERS', '1637 Lincolnway Square', 'Suite #100765', '', '1995-11-01', 'Carnesville', 'USA', 'Jenny', 'McGuire', 'Dana', 'Student', '', 'WV', '', '30255' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '473 Briarbush Ave', '', '', '1993-08-03', 'Weber', 'USA', 'Thomas', 'McGuire', 'Tim', 'Student', 'II', 'OR', '', '60946' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '567 Ledbetter Trail', '', '', '1974-03-01', 'Eulonia', 'USA', 'Janice', 'McGuire', 'Jill', 'Human Resources', '', 'MS', 'Director', '34593' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1e58d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1004 Waters Station', 'Apt #100478', '', '1973-11-01', 'Baxley', 'USA', 'Tim', 'McGuire', 'Thomas', 'Architect', '', 'AL', 'Chief', '25221' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'DAUGHTERS', '1217 Carbondale Ave', '', '', '1992-08-01', 'Wray', 'USA', 'Rocky', 'Bender', 'Tam', 'Sales', '', 'CT', 'VP', '36292' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '505 Humphrey Drv', '', '', '1992-08-01', 'Sunsweet', 'USA', 'Rocky', 'Bender', 'Tam', 'Sales', '', 'HI', 'VP', '77562-7805' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2361e0-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '933 Moxahala Place', '', '', '1928-07-14', 'Augusta', 'USA', 'Edward', 'Campbell', 'Bill', 'Nurse', 'II', 'KS', 'Level V', '72080' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2361e0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '662 Powell Circle', 'Suite #100932', '', '1928-07-14', 'Covington', 'USA', 'Edward', 'Campbell', 'Bill', 'Nurse', 'II', 'HI', 'Level V', '18685' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2361e0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1376 Ayers Drv', '', '', '1928-07-14', 'Ringgold', 'USA', 'Edward', 'Campbell', 'Bill', 'Nurse', 'II', 'AL', 'Level V', '14252' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20a2c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1104 Carlysle Lane', 'Apt #100793', '', '1945-03-07', 'Centerville', 'USA', 'Carrie', 'McDowell', 'Brenda', 'Doctor', 'Sr', 'MS', 'CEO', '68771' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20a2c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1297 Kimes Way', 'Suite #100830', '', '1945-03-07', 'Abba', 'USA', 'Carrie', 'McDowell', 'Brenda', 'Doctor', 'Sr', 'OK', 'CEO', '32783' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20a2c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1784 Spielbusch Run', 'Apt #100112', '', '1945-03-07', 'Pineora', 'USA', 'Carrie', 'McDowell', 'Brenda', 'Doctor', 'Sr', 'PR', 'CEO', '43998' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '931 Dana Blvd', '', '', '1957-06-19', 'Saint George', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'MA', 'Senior', '50345' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'MOTHERS', '999 Ward Ridge', '', '', '1957-06-19', 'Enigma', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'VT', 'Senior', '21683' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '694 Heber Place', '', '', '1957-06-19', 'Sunsweet', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'ID', 'Senior', '23413' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c233ad0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1147 Homeless Court', 'Apt #10089', '', '1957-06-19', 'Rebecca', 'USA', 'Brittany', 'Sweeney', 'Nicole', 'Analyst', 'Jr', 'OH', 'Senior', '42596-8790' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21b430-ff7c-11e6-9008-a1d8a5a6f0cc, 'FATHERS', '1593 Swingle Square', 'Apt #100297', '', '1929-07-12', 'Ludowici', 'USA', 'Barb', 'Maddox', 'Chelsea', 'Business', 'I', 'IN', 'Lead', '60595' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21b430-ff7c-11e6-9008-a1d8a5a6f0cc, 'MOTHERS', '809 Brown Court', 'Suite #100723', '', '1929-07-12', 'Cuthbert', 'USA', 'Barb', 'Maddox', 'Chelsea', 'Business', 'I', 'CT', 'Lead', '97685' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21b430-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '984 Nottingham Rd', '', '', '1929-07-12', 'Dock Junction', 'USA', 'Barb', 'Maddox', 'Chelsea', 'Business', 'I', 'ND', 'Lead', '50897' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ece00-ff7c-11e6-9008-a1d8a5a6f0cc, 'GRANDPARENTS', '1038 Merlin Boulevard', 'Apt #100894', '', '1987-06-14', 'Alapaha', 'USA', 'Stephanie', 'Fry', 'Antonio', 'Sales', 'Sir', 'TN', 'Lead', '22264' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f4330-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '1546 Woodbrook Run', 'Apt #100144', '', '1953-11-22', 'Stone Mountain', 'USA', 'Brian', 'Jefferson', 'Dusty', 'Marine', 'Sr', 'GA', '', '19501' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f4330-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '937 Jonathan Drv', '', '', '1953-11-22', 'Rutledge', 'USA', 'Brian', 'Jefferson', 'Dusty', 'Marine', 'Sr', 'KY', '', '34613' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f4330-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1485 Hickam St', 'Suite #100501', '', '1953-11-22', 'Soperton', 'USA', 'Brian', 'Jefferson', 'Dusty', 'Marine', 'Sr', 'MT', '', '59697' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c19ec00-ff7c-11e6-9008-a1d8a5a6f0cc, 'FATHERS', '828 Arch Avenue', '', '', '1907-09-28', 'Soperton', 'USA', 'Lindsey', 'Craft', 'Erica', 'Human Resources', 'II', 'TN', 'Analyst', '61211' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c19ec00-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1552 Lashley Drive', 'Suite #100771', '', '1907-09-28', 'Kennesaw', 'USA', 'Lindsey', 'Craft', 'Erica', 'Human Resources', 'II', 'GA', 'Analyst', '88109' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ea6f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1567 Ballov Drive', '', '', '1964-09-14', 'Jeffersonville', 'USA', 'Nancy', 'Stevens', 'Chasity', 'Finance', '', 'NV', 'VP', '51222' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ea6f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '425 Robinwood Place', '', '', '1964-09-14', 'Colesburg', 'USA', 'Nancy', 'Stevens', 'Chasity', 'Finance', '', 'UT', 'VP', '12799' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1ea6f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1166 Adamsville Lane', 'Apt #100756', '', '1964-09-14', 'Cobbtown', 'USA', 'Nancy', 'Stevens', 'Chasity', 'Finance', '', 'VA', 'VP', '82623' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c229e90-ff7c-11e6-9008-a1d8a5a6f0cc, 'DAUGHTERS', '636 Shasta Crescent', 'Apt #10022', '', '1943-12-23', 'Lovejoy', 'USA', 'Justin', 'Henry', 'Juanita', 'Astronaut', 'II', 'OH', 'Dr', '21628' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c229e90-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '517 Rhonda Road', '', '', '1943-12-23', 'Blackshear', 'USA', 'Justin', 'Henry', 'Juanita', 'Astronaut', 'II', 'TX', 'Dr', '85221' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c202d90-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1113 Willow Circle', '', '', '1919-09-19', 'Sandersville', 'USA', 'Kylie', 'Adams', 'Gabriel', 'Human Resources', '', 'WI', 'Mam', '89988' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2388f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '494 Stevens Park', '', '', '1974-11-21', 'Toomsboro', 'USA', 'Sue', 'Ferrell', 'Andy', 'Sales', 'III', 'IA', 'Dr', '14756' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2388f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1586 Sycamore Ave', '', '', '1974-11-21', 'Montrose', 'USA', 'Sue', 'Ferrell', 'Andy', 'Sales', 'III', 'MT', 'Dr', '68528' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2388f0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WORK', '434 Mackenzie Path', 'Suite #100659', '', '1974-11-21', 'Homer', 'USA', 'Sue', 'Ferrell', 'Andy', 'Sales', 'III', 'WA', 'Dr', '95271' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c23b000-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '1525 Lancaster-Chillicoth Drive', 'Suite #100555', '', '1916-09-14', 'Ray City', 'USA', 'Jessie', 'Cortez', 'Dusty', 'Pilot', '', 'GA', 'Level I', '78780' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c23b000-ff7c-11e6-9008-a1d8a5a6f0cc, 'WORK', '1056 Stansberry Terrace', '', '', '1916-09-14', 'Darien', 'USA', 'Jessie', 'Cortez', 'Dusty', 'Pilot', '', 'FL', 'Level I', '45996' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f6a40-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1291 Wolford Drive', '', '', '2010-11-10', 'Thomson', 'USA', 'Marty', 'Franco', 'Sam', 'Homemaker', '', 'CA', 'Head', '27817' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f6a40-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '527 Deerfield Drive', 'Apt #100674', '', '2010-11-10', 'Buford', 'USA', 'Marty', 'Franco', 'Sam', 'Homemaker', '', 'NE', 'Head', '76099' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c207bb0-ff7c-11e6-9008-a1d8a5a6f0cc, 'GRANDPARENTS', '1642 Fess Crescent', '', '', '1929-11-02', 'Hinesville', 'USA', 'Erin', 'Stafford', 'Leroy', 'Marine', '', 'IN', 'Level V', '88743' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c207bb0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '746 Gallia Avenue', 'Apt #100987', '', '1929-11-02', 'Queensland', 'USA', 'Erin', 'Stafford', 'Leroy', 'Marine', '', 'OR', 'Level V', '16294' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c207bb0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '571 Marne Lane', 'Suite #100514', 'Suite #100984', '1929-11-02', 'Bowens Mill', 'USA', 'Erin', 'Stafford', 'Leroy', 'Marine', '', 'MS', 'Level V', '23800' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a1-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '574 Seaman Heights', 'Suite #100818', '', '1982-09-03', 'Camilla', 'USA', 'Ronda', 'Hopper', 'Lance', 'Education', '', 'PA', 'Level IV', '50184' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c22c5a1-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '1280 Keen Way', 'Suite #100753', '', '1982-09-03', 'Douglasville', 'USA', 'Ronda', 'Hopper', 'Lance', 'Education', '', 'WA', 'Level IV', '71098' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20c9d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'EXS', '763 Talley Circle', 'Suite #100149', 'Apt #100422', '1961-05-26', 'Smithville', 'USA', 'Jeremiah', 'Bird', 'Kris', 'Spy', '', 'VA', 'Head', '90070' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20c9d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1420 Mcarthur Park', 'Suite #10089', '', '1961-05-26', 'Mcrae', 'USA', 'Jeremiah', 'Bird', 'Kris', 'Spy', '', 'NV', 'Head', '75592' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c20c9d0-ff7c-11e6-9008-a1d8a5a6f0cc, 'VACATION', '1320 Sunflower Trail', '', '', '1961-05-26', 'Auburn', 'USA', 'Jeremiah', 'Bird', 'Kris', 'Spy', '', 'VT', 'Head', '15238' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21db40-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '1711 Scenic Park', 'Suite #10074', '', '1909-12-31', 'Griffin', 'USA', 'Douglas', 'Shelton', 'Patty', 'IT', 'III', 'NH', 'Chief', '65122' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c21db40-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '517 Frame Crescent', 'Apt #100190', '', '1909-12-31', 'Colesburg', 'USA', 'Douglas', 'Shelton', 'Patty', 'IT', 'III', 'NM', 'Chief', '17464' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f9150-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1643 Dietz Street', 'Apt #100526', '', '1924-05-13', 'Forsyth', 'USA', 'Myron', 'Blanchard', 'Rod', 'Chef', '', 'RI', 'Head', '30016' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1f9150-ff7c-11e6-9008-a1d8a5a6f0cc, 'WORK', '1217 Sofin Street', '', '', '1924-05-13', 'Athens', 'USA', 'Myron', 'Blanchard', 'Rod', 'Chef', '', 'UT', 'Head', '30185' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c213f00-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '1176 Sheila Ln', '', '', '1950-03-03', 'Sylvania', 'USA', 'Tristan', 'Curry', 'Jade', 'Finance', '', 'NC', 'Dr', '18172' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c213f00-ff7c-11e6-9008-a1d8a5a6f0cc, 'SPOUSE', '1288 Milton Rd', '', '', '1950-03-03', 'Kinderlou', 'USA', 'Tristan', 'Curry', 'Jade', 'Finance', '', 'MA', 'Dr', '43762' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c213f00-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '886 Beech Station', 'Suite #100619', '', '1950-03-03', 'Alapaha', 'USA', 'Tristan', 'Curry', 'Jade', 'Finance', '', 'ID', 'Dr', '95739' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'EXS', '790 Cemetery Circle', '', '', '1941-07-19', 'Axson', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'NC', 'Senior', '19445-2314' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1767 Blackburn Street', '', '', '1941-07-19', 'Lake City', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'CT', 'Senior', '35780' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '993 Ridgefield Path', 'Apt #100385', '', '1941-07-19', 'Claxton', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'TX', 'Senior', '60020' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c2313c0-ff7c-11e6-9008-a1d8a5a6f0cc, 'WINTER', '1665 Kevrob Ave', '', '', '1941-07-19', 'Thalman', 'USA', 'Norman', 'Buckner', 'Charles', 'Engineer', '', 'MA', 'Senior', '66172' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225070-ff7c-11e6-9008-a1d8a5a6f0cc, 'EXS', '832 Cooperriders St', '', '', '1922-02-04', 'Hopeulikit', 'USA', 'Ruth', 'Kinney', 'Craig', 'Spy', '', 'GU', 'Director', '85914' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c225070-ff7c-11e6-9008-a1d8a5a6f0cc, 'HOME', '1475 Taylor Street', 'Apt #100345', '', '1922-02-04', 'Lookout Mountain', 'USA', 'Ruth', 'Kinney', 'Craig', 'Spy', '', 'WA', 'Director', '98578' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'GRANDPARENTS', '772 Evergreen St', '', '', '1905-03-03', 'Baden', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'DE', 'Analyst', '86686' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'PARTNERS', '1789 Amsterdam Way', '', '', '1905-03-03', 'Rockmart', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'IN', 'Analyst', '14557' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'ROOMATE', '877 Green Rd', '', '', '1905-03-03', 'Lawrenceville', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'TX', 'Analyst', '30221' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c220250-ff7c-11e6-9008-a1d8a5a6f0cc, 'SONS', '686 Talford Station', '', '', '1905-03-03', 'Mcrae', 'USA', 'Cassandra', 'Lindsay', 'Ray', 'IT', '', 'OH', 'Analyst', '59657' );

INSERT INTO user_address_multiple_solr ( unique, address_name, address1, address2, address3, birthday, city, country, first_name, last_name, mid_name, occupation, ordinal, state, title, zip )
VALUES ( 7c1fb860-ff7c-11e6-9008-a1d8a5a6f0cc, 'SUMMER', '808 Ashley Ave', '', 'Suite #100914', '1961-09-28', 'Morven', 'USA', 'Michael', 'Gross', 'Richard', 'Marine', '', 'DE', 'Sir', '54212' );

SELECT COUNT(*) FROM user_address_multiple_solr;

CREATE SEARCH INDEX IF NOT EXISTS ON bootcamp.user_address_multiple_solr WITH COLUMNS last_name, first_name, address_name, city, state;

SELECT * FROM user_address_multiple_solr WHERE solr_query = 'last_name:M*';

    