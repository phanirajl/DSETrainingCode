CREATE KEYSPACE IF NOT EXISTS bootcamp WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};


CREATE TABLE IF NOT EXISTS primary_key_example (
    id timeuuid,
    pk2 text,
    cc1 text,
    cc2 text,
    rand_text text,
    rand_int int,
    rand_time timestamp,
    rand_map map<text, text>,
    rand_list list<text>,
    rand_set set<text>,
    PRIMARY KEY (( id, pk2 ), cc1, cc2)
);

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 6996d330-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY10', 'CLUSTER08', 'EBYWYYPGQORIGHF', -472746616, [ 'shepherd',
'we', 'list' ], {
    'have' : 'came',
    'or maybe' : 'room',
    'to' : 'captain'
}, { 'monster' }, 'television', '1982-09-09 04:50:23.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699a55a0-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY02', 'CLUSTER10', 'NAHZXGXZZDURIFE', -2076878841, [ 'suspense'
], {
    'or' : 'dreams',
    'the' : 'badly',
    'wicket' : 'do'
}, { 'discovered' }, 'television', '1913-10-30 20:55:52.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699a7cb0-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY01', 'CLUSTER02', 'BQZQAPHGVJJFHZC', 53438720, [ 'days',
'enter', 'it' ], {
    'monster' : 'where'
}, { 'about', 'asked' }, 'discovered', '2013-03-17 22:22:07.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699a7cb1-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY04', 'CLUSTER10', 'PQXQDGQGQRYYSGQ', 1262558601, [ 'of', 'died',
'end' ], {
    'monster' : 'always',
    'numbers' : 'have',
    'what' : 'assert'
}, { 'what' }, 'constantly', '1988-06-25 01:05:44.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699a7cb2-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY02', 'CLUSTER04', 'RKSDTRFTRGQXNOZ', 1807743463, [ 'warp' ], {
    'good' : 'good',
    'magnetic' : 'with',
    'much' : 'waited',
    'trees' : 'end'
}, { 'build' }, 'constantly', '1944-04-24 04:18:44.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699b6710-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY08', 'CLUSTER07', 'AFVCYQCODILPWBN', 417068235, [ 'so', 'this',
'probably', 'to' ], {
    'have' : 'hat',
    'warp' : 'throw'
}, { 'hid' }, 'television', '1919-09-19 08:47:46.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699bdc40-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY02', 'CLUSTER10', 'TAXFFVNZAHNYNHN', -2113076627, [ 'plane' ], {
    'in' : 'library',
    'secretly' : 'lock',
    'we' : 'white'
}, { 'constantly' }, 'discovered', '1982-04-07 15:00:48.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699c9f90-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY07', 'CLUSTER09', 'FWJEIELPEYXHBUF', 275755746, [ 'assert',
'ball', 'of', 'throw', 'came' ], {
    'automatically' : 'what',
    'of' : 'to',
    'something' : 'room',
    'what' : 'pique'
}, { 'about', 'through', 'to' }, 'constantly', '1974-03-11 07:11:13.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699d14c0-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY04', 'CLUSTER03', 'WBQKVDYJUQZULXD', -2013075260, [ ], {
    'find' : 'light',
    'good' : 'shepherd',
    'numbers' : 'found',
    'what' : 'hold'
}, { 'have', 'headphones', 'probably' }, 'constantly', '1990-07-12 10:11:15.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699d3bd0-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER08', 'UVPVJYADOAPVSZD', -1747316462, [ 'sacrifice'
], {
    'days' : 'of',
    'do' : 'shepherd',
    'good' : 'want',
    'of' : 'asked',
    'text' : 'crash',
    'web' : 'of'
}, { 'hot', 'of', 'so' }, 'television', '1928-07-15 07:22:41.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699d89f0-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY06', 'CLUSTER01', 'GOCCEMREEQDLOWW', -396628951, [ 'pull', 'to',
'this', 'something', 'they' ], {
    'books' : 'to',
    'end' : 'ball',
    'is' : 'on'
}, { 'as', 'dad' }, 'television', '2011-09-16 22:01:27.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER06', 'WAPGXWNKZEQGMOP', -646332032, [ 'room' ], {
    'in' : 'hatch'
}, { 'came' }, 'constantly', '1986-10-08 05:33:06.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699f37a0-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER10', 'PMOLFEFQBMUPGSU', 2035139963, [ 'sacrifice',
'so', 'hot', 'of', 'sidekick', 'this' ], {
    'good' : 'want',
    'of' : 'asked',
    'text' : 'crash'
}, { 'ill', 'island' }, 'constantly', '1924-11-18 11:44:19.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699f5eb0-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY07', 'CLUSTER06', 'VOPTWMMNTJFUEGD', -414501573, [ 'have',
'want' ], {
    'boon' : 'hot',
    'or' : 'world'
}, { 'captain', 'demanded', 'interest', 'or maybe', 'to', 'what' }, 'constantly', '1915-04-07 12:50:49.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699f85c0-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY04', 'CLUSTER01', 'FQOVFTYLJMXIXTD', 1138143367, [ 'find',
'light', 'what' ], {
    'a' : 'moved',
    'good' : 'shepherd',
    'hold' : 'computer',
    'numbers' : 'found'
}, { 'have', 'headphones', 'hold', 'probably' }, 'constantly', '1948-12-21 16:42:48.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699facd0-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY03', 'CLUSTER03', 'OIXAXGGVAXCSJLL', -629077146, [ 'dreams',
'suspense', 'discovered', 'text' ], {
    'badly' : 'or',
    'do' : 'the',
    'wrong' : 'wicket'
}, { }, 'discovered', '1930-07-28 17:17:40.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 69a10c60-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY03', 'CLUSTER01', 'KKJOPJUSTXYQVSI', -1298835870, [ 'secretly'
], { }, { 'library', 'out', 'sacrifice' }, 'television', '1908-11-08 01:07:27.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 69a10c61-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY10', 'CLUSTER01', 'MDTWSADOHMTGOZZ', 1141143680, [ 'end' ], {
    'asked' : 'about',
    'automatically' : 'as',
    'came' : 'monster',
    'enter' : 'it',
    'sofa' : 'what',
    'where' : 'days',
    'with' : 'to'
}, { 'discovered', 'list' }, 'television', '1950-03-05 11:48:37.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 69a13370-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY04', 'CLUSTER02', 'TVHRYYBLTLHDSMQ', 949876790, [ 'assert',
'ball' ], {
    'automatically' : 'what',
    'something' : 'room'
}, { 'came', 'of', 'through', 'throw' }, 'constantly', '2005-06-14 15:23:02.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 69a15a80-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY07', 'CLUSTER01', 'NLLKLKMCWPQNBEB', -610315129, [ 'have',
'came' ], {
    'captain' : 'as',
    'hat' : 'it''s',
    'hid' : 'ill',
    'library' : 'lock'
}, { 'or maybe' }, 'constantly', '1924-01-23 22:30:17.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 781267d0-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY10', 'CLUSTER08', 'EBYWYYPGQORIGHF', -1592479427, [ 'caves' ], {
    'constantly' : 'this',
    'library' : 'we',
    'lock' : 'plane',
    'rescue' : 'in',
    'white' : 'secretly'
}, { 'came', 'captain', 'sits', 'speed' }, 'discovered', '1914-09-07 03:41:43.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 78165f70-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY10', 'CLUSTER07', 'HZXGXZZDURIFEUB', -1609437428, [ 'wrong',
'window', 'library' ], {
    'list' : 'crash'
}, { 'caves', 'library' }, 'discovered', '1943-12-25 06:14:59.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 78165f71-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY02', 'CLUSTER01', 'RPBQZQAPHGVJJFH', 1450202892, [ 'came',
'through', 'about', 'to', 'sidekick', 'man' ], {
    'assert' : 'ball',
    'of' : 'throw',
    'something' : 'room'
}, { 'smoke' }, 'constantly', '1934-11-21 10:07:44.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 78165f72-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY03', 'CLUSTER08', 'COEZHXPQXQDGQGQ', -781762463, [ 'a' ], {
    'always' : 'are',
    'do' : 'hid',
    'hold' : 'computer'
}, { 'found', 'good', 'moved', 'numbers' }, 'constantly', '1945-10-25 01:31:17.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 78168680-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY04', 'CLUSTER04', 'RGQXNOZOCHTXMZN', 1785175405, [ 'hat', 'so'
], { }, { 'probably', 'this' }, 'television', '1900-01-20 20:25:39.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 781749d0-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY08', 'CLUSTER07', 'AFVCYQCODILPWBN', 474144713, [ ], {
    'came' : 'leader',
    'ends' : 'was',
    'sidekick' : 'on',
    'they' : 'are',
    'this' : 'keys',
    'write' : 'write'
}, { 'handled', 'of', 'people' }, 'headphones', '1937-06-09 16:39:06.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 781797f0-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY03', 'CLUSTER01', 'HYVFAHRSMYWDBCQ', 2024375286, [ 'of',
'sidekick', 'this' ], {
    'so' : 'hot',
    'want' : 'sacrifice'
}, { 'ill', 'island', 'who' }, 'discovered', '1914-09-26 12:32:24.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 78188250-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY04', 'CLUSTER09', 'BNWNGMJVJLEDWPY', 415870658, [ 'web',
'banging', 'have', 'list' ], {
    'moved' : 'man'
}, { }, 'constantly', '1994-02-15 16:42:19.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 7818f780-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY06', 'CLUSTER09', 'PGPLDNOBTTNGDXU', 809000459, [ 'sidekick',
'on', 'write' ], {
    'ends' : 'was'
}, { 'came', 'write' }, 'headphones', '1937-06-09 16:39:01.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 78191e90-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY03', 'CLUSTER03', 'YXEFUMGBAFXDOPS', 1786304538, [ 'write' ], {
    'ends' : 'was',
    'sidekick' : 'on'
}, { 'came', 'keys', 'leader', 'this', 'write' }, 'headphones', '1937-06-09 16:38:56.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 78196cb0-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY09', 'CLUSTER03', 'DLOWWYNTGZTKBMI', 1956948800, [ 'worn',
'demanded' ], {
    'with' : 'write'
}, { 'probably', 'smoke', 'web', 'what' }, 'constantly', '2014-10-19 09:43:40.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 781993c0-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY05', 'CLUSTER09', 'ZEQGMOPSFSIPQAC', -1102148814, [ 'handled',
'discovered', 'they' ], { }, { 'badly', 'from', 'or', 'so' }, 'television', '1908-06-17 18:23:21.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 7819e1e0-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY08', 'CLUSTER07', 'FQBMUPGSUWYQEFO', -1721695521, [ 'this' ], {
    'books' : 'to',
    'is' : 'on',
    'pull' : 'to'
}, { 'as', 'dad', 'something', 'they', 'what' }, 'constantly', '1923-12-07 18:10:00.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 781a08f0-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY05', 'CLUSTER04', 'TJFUEGDXVTEWOSR', -1673539049, [ 'end',
'what' ], {
    'of' : 'died'
}, { 'about', 'dad', 'hot', 'ill', 'light', 'ship' }, 'discovered', '1930-11-21 06:06:44.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 781a3000-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY02', 'CLUSTER04', 'ZTUOFQOVFTYLJMX', 886082669, [ 'always',
'of', 'died', 'end', 'what', 'dad' ], {
    'assert' : 'numbers',
    'have' : 'monster'
}, { }, 'constantly', '2001-03-23 12:39:34.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 781a7e20-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY03', 'CLUSTER09', 'CPJEMKOHLKLWOIX', -1820942319, [ 'warp',
'build', 'with', 'who', 'have' ], {
    'good' : 'good',
    'magnetic' : 'with',
    'much' : 'waited',
    'trees' : 'end'
}, { 'days', 'keys', 'man', 'when', 'will' }, 'constantly', '1944-04-24 04:18:38.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 781b4170-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY07', 'CLUSTER08', 'TZLMEXVEEPPKCZQ', -1536047318, [ 'what',
'wrong', 'of' ], {
    'hatch' : 'of',
    'when' : 'much',
    'with' : 'warp'
}, { }, 'constantly', '1979-11-05 06:37:15.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 781b6880-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY02', 'CLUSTER02', 'MMVNPWBHPYVZASA', 1677835034, [ 'badly' ], {
    'end' : 'it'
}, { 'I', 'monster', 'red', 'ship', 'through' }, 'discovered', '1997-05-21 07:37:30.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 781b6881-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY05', 'CLUSTER04', 'EHTVHRYYBLTLHDS', 1107661953, [ 'my',
'until', 'rabbit', 'from' ], {
    'no' : 'about'
}, { 'hot', 'many' }, 'television', '1916-09-17 00:17:57.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 781b8f90-05ae-11e7-ba0f-21dd452c7333, 'PARTKEY01', 'CLUSTER01', 'MCWPQNBEBBUPXMI', -2103415660, [ 'of',
'it''s', 'secretly', 'library' ], {
    'banging' : 'to',
    'do' : 'something',
    'is' : 'on',
    'on' : 'ship',
    'to' : 'much',
    'where' : 'in'
}, { }, 'constantly', '1993-11-04 22:40:30.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e10b50-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY10', 'CLUSTER08', 'EBYWYYPGQORIGHF', 322606570, [ 'books',
'speed', 'discovered' ], {
    'interest' : 'I',
    'until' : 'numbers'
}, { 'a', 'asked' }, 'television', '1995-01-26 01:03:22.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e63b70-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY07', 'CLUSTER01', 'THAJNAHZXGXZZDU', 1628158156, [ 'I' ], {
    'red' : 'through'
}, { 'smoke' }, 'television', '1962-07-28 16:06:18.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e68990-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY07', 'CLUSTER02', 'FARPBQZQAPHGVJJ', 581154281, [ 'discovered',
'a' ], {
    'books' : 'speed',
    'until' : 'numbers'
}, { 'asked', 'ship', 'sidekick', 'text' }, 'constantly', '1903-05-22 09:23:46.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e725d0-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY05', 'CLUSTER09', 'RYXZLWWQLBKVRKS', -476172269, [ 'write',
'write', 'came' ], {
    'ends' : 'was',
    'sidekick' : 'on'
}, { 'keys', 'leader', 'they', 'this' }, 'headphones', '1937-06-09 16:39:11.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e74ce0-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY01', 'CLUSTER04', 'HBUXUMJZBAQTQUJ', -1184909703, [ 'library',
'we' ], {
    'rescue' : 'in'
}, { 'lock', 'secretly', 'white' }, 'discovered', '1914-09-07 03:41:50.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e79b00-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY05', 'CLUSTER04', 'WBNLOYZEIZCBHEK', -579083710, [ 'trees',
'end' ], { }, { 'build', 'warp' }, 'constantly', '1922-07-26 09:40:47.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e7c210-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY04', 'CLUSTER08', 'RSMYWDBCQZSAFIF', 1674956211, [ 'captain',
'sits', 'came', 'speed', 'open', 'handled', 'died' ], {
    'plane' : 'constantly',
    'secretly' : 'lock',
    'this' : 'caves',
    'we' : 'white'
}, { 'when' }, 'discovered', '1987-07-03 04:19:54.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e81030-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY10', 'CLUSTER04', 'NMTPVEBNWNGMJVJ', -681474016, [ 'with', 'to'
], {
    'asked' : 'about',
    'automatically' : 'as',
    'sofa' : 'what'
}, { 'discovered', 'end' }, 'constantly', '1957-09-25 09:15:32.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e85e50-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY07', 'CLUSTER06', 'CTRPXUPGPLDNOBT', -71509436, [ 'want',
'or maybe', 'interest' ], {
    'from' : 'or',
    'hot' : 'have',
    'world' : 'boon'
}, { 'captain', 'demanded', 'to' }, 'constantly', '1910-12-29 21:20:59.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e88560-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY08', 'CLUSTER02', 'UMGBAFXDOPSPBXH', -1058216256, [ 'what' ], {
    'demanded' : 'web',
    'probably' : 'smoke'
}, { 'others', 'the' }, 'constantly', '1974-07-29 16:38:12.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e8ac70-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY03', 'CLUSTER08', 'OWWYNTGZTKBMIPP', 1476053350, [ 'room',
'came' ], {
    'generated' : 'discovered',
    'in' : 'hatch'
}, { }, 'television', '2009-11-29 19:22:24.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e8fa90-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY09', 'CLUSTER01', 'QGMOPSFSIPQACNK', -1519105999, [ 'enter' ], {
    'came' : 'monster',
    'where' : 'days'
}, { 'about', 'asked', 'it' }, 'television', '1950-03-05 11:48:26.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e948b0-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY10', 'CLUSTER05', 'OLFEFQBMUPGSUWY', -480103094, [ 'as' ], {
    'is' : 'on',
    'pull' : 'to',
    'they' : 'dad',
    'this' : 'something'
}, { }, 'constantly', '2004-04-25 12:16:04.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e96fc0-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY05', 'CLUSTER06', 'OSZMDEUHXOMLVOP', 622479441, [ 'light',
'while', 'is' ], {
    'generated' : 'smoke',
    'my' : 'handled',
    'waited' : 'have'
}, { 'days', 'no', 'of' }, 'discovered', '1910-04-17 16:22:24.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e996d0-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY09', 'CLUSTER01', 'JBBDUNIDFLIQYEW', -316285529, [ 'until' ], {
    'is' : 'lock',
    'island' : 'my'
}, { 'of' }, 'constantly', '1955-04-02 12:38:30.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80e9bde0-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY03', 'CLUSTER09', 'CPJEMKOHLKLWOIX', 1580554888, [ 'rabbit',
'from', 'hot', 'many' ], {
    'my' : 'until',
    'no' : 'about'
}, { 'I', 'interest', 'library', 'numbers', 'until' }, 'television', '1916-09-17 00:18:08.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80ea5a20-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY03', 'CLUSTER01', 'KKJOPJUSTXYQVSI', -474258411, [ 'of', 'came',
'wicket', 'ship' ], { }, { 'end', 'what' }, 'constantly', '2001-11-07 02:59:57.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80ea8130-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY09', 'CLUSTER03', 'DOHMTGOZZXMMVNP', -1880617735, [ 'web',
'banging', 'have' ], {
    'dreams' : 'probably',
    'moved' : 'man',
    'ship' : 'sidekick',
    'text' : 'good'
}, { 'crash', 'list', 'wrong' }, 'constantly', '1921-08-01 04:32:42.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80eaa840-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY08', 'CLUSTER09', 'JMGGSYYNEHTVHRY', -1579471462, [ 'open',
'handled', 'died' ], {
    'came' : 'speed'
}, { 'hatch', 'much', 'when' }, 'television', '1930-07-12 05:24:08.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 80eacf50-05ae-11e7-bf9b-d7d4df194c8a, 'PARTKEY01', 'CLUSTER01', 'YHEWHWNLLKLKMCW', 1651702170, [ 'lock',
'until' ], {
    'came' : 'island',
    'my' : 'is',
    'sill' : 'came'
}, { 'do', 'dreams', 'is', 'much', 'of', 'to' }, 'discovered', '1918-02-04 03:23:25.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad14b70-05ae-11e7-96a9-c5968f521666, 'PARTKEY10', 'CLUSTER08', 'EBYWYYPGQORIGHF', 455935776, [ 'or', 'world',
'boon' ], {
    'handled' : 'discovered',
    'so' : 'from',
    'they' : 'badly'
}, { 'have', 'hot', 'or maybe', 'want' }, 'television', '1908-06-17 18:23:15.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad458b0-05ae-11e7-96a9-c5968f521666, 'PARTKEY10', 'CLUSTER02', 'IFEUBRHHQAJAIOH', 1161523445, [ 'in', 'in',
'trying', 'plane' ], {
    'came' : 'they',
    'discovered' : 'in',
    'hatch' : 'room',
    'shepherd' : 'trying',
    'sill' : 'they',
    'will' : 'generated'
}, { 'ball', 'ghost', 'warp' }, 'constantly', '1948-05-25 23:43:28.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad4cde0-05ae-11e7-96a9-c5968f521666, 'PARTKEY02', 'CLUSTER08', 'CPOZSGFCTJNDDMH', -188955547, [ 'to',
'captain', 'shepherd', 'we' ], {
    'hat' : 'it''s',
    'have' : 'came',
    'or maybe' : 'room'
}, { 'list', 'monster' }, 'television', '2010-02-02 00:13:52.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad54310-05ae-11e7-96a9-c5968f521666, 'PARTKEY09', 'CLUSTER05', 'XZLWWQLBKVRKSDT', -1028064238, [ 'find' ], {
    'a' : 'moved',
    'always' : 'are',
    'do' : 'hid',
    'good' : 'shepherd',
    'hold' : 'computer',
    'numbers' : 'found'
}, { 'light' }, 'constantly', '1945-10-25 01:31:32.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad56a20-05ae-11e7-96a9-c5968f521666, 'PARTKEY08', 'CLUSTER01', 'SRHBUXUMJZBAQTQ', 692283989, [ 'came' ], {
    'while' : 'of'
}, { 'ball', 'end', 'ship', 'what', 'wicket' }, 'television', '1913-10-09 09:03:20.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad5b840-05ae-11e7-96a9-c5968f521666, 'PARTKEY05', 'CLUSTER04', 'WBNLOYZEIZCBHEK', 1287090511, [ 'is', 'to',
'much' ], {
    'of' : 'dreams'
}, { }, 'discovered', '1986-05-26 21:01:05.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad5df50-05ae-11e7-96a9-c5968f521666, 'PARTKEY02', 'CLUSTER03', 'CWHYVFAHRSMYWDB', -1838256302, [ 'through',
'I', 'smoke', 'many', 'worn' ], {
    'monster' : 'red'
}, { }, 'constantly', '1960-09-09 15:08:24.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad60660-05ae-11e7-96a9-c5968f521666, 'PARTKEY05', 'CLUSTER06', 'YBLDYPNMTPVEBNW', 1571777122, [ 'plane',
'rescue', 'in' ], {
    'do' : 'days',
    'enter' : 'people'
}, { }, 'constantly', '1934-12-22 14:49:54.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad62d70-05ae-11e7-96a9-c5968f521666, 'PARTKEY04', 'CLUSTER02', 'UQZULXDSTQUKMPC', -142927179, [ ], {
    'caves' : 'library',
    'ill' : 'captain',
    'lock' : 'hid',
    'window' : 'library'
}, { 'as', 'hat', 'it''s' }, 'discovered', '2001-04-18 11:35:16.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad65480-05ae-11e7-96a9-c5968f521666, 'PARTKEY05', 'CLUSTER06', 'SZDANSPSYOYXEFU', 1489343452, [ 'are' ], {
    'badly' : 'always',
    'have' : 'days',
    'ill' : 'they',
    'keys' : 'man',
    'when' : 'will'
}, { 'do', 'hid' }, 'constantly', '1998-12-26 16:18:57.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad6a2a0-05ae-11e7-96a9-c5968f521666, 'PARTKEY03', 'CLUSTER09', 'EQDLOWWYNTGZTKB', -1465256611, [ 'build',
'with', 'who', 'have' ], { }, { 'days' }, 'constantly', '1990-08-23 18:45:07.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad6f0c0-05ae-11e7-96a9-c5968f521666, 'PARTKEY04', 'CLUSTER09', 'RXWAPGXWNKZEQGM', 1415919306, [ 'while', 'is'
], {
    'have' : 'generated',
    'smoke' : 'light'
}, { 'no' }, 'constantly', '1957-06-22 03:57:08.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad73ee0-05ae-11e7-96a9-c5968f521666, 'PARTKEY09', 'CLUSTER10', 'PMOLFEFQBMUPGSU', -49812226, [ 'came',
'they', 'shepherd', 'trying' ], {
    'discovered' : 'in',
    'hatch' : 'room'
}, { }, 'constantly', '1966-05-31 14:13:39.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad765f0-05ae-11e7-96a9-c5968f521666, 'PARTKEY09', 'CLUSTER02', 'YQJJOSZMDEUHXOM', -1779887833, [ 'end',
'discovered', 'list' ], {
    'asked' : 'about',
    'automatically' : 'as',
    'sofa' : 'what',
    'with' : 'to'
}, { 'hot', 'interest', 'no' }, 'constantly', '1957-09-25 09:15:24.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad7b410-05ae-11e7-96a9-c5968f521666, 'PARTKEY07', 'CLUSTER07', 'PEGYJBBDUNIDFLI', -1594471352, [ 'of', 'to'
], {
    'hatch' : 'of',
    'what' : 'wrong',
    'with' : 'warp'
}, { 'what' }, 'constantly', '2004-12-14 12:21:10.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad80230-05ae-11e7-96a9-c5968f521666, 'PARTKEY10', 'CLUSTER03', 'XIXTDOIIRRKMXGY', -1693331120, [ 'what',
'the', 'others', 'others' ], {
    'probably' : 'smoke'
}, { 'good', 'pull', 'ship' }, 'constantly', '1952-09-23 21:00:03.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad8ec90-05ae-11e7-96a9-c5968f521666, 'PARTKEY04', 'CLUSTER09', 'JLLTVGRGQRVOYLD', -97507692, [ 'about', 'to',
'sidekick', 'man' ], {
    'came' : 'through'
}, { 'code', 'smoke', 'television' }, 'constantly', '1957-09-13 05:33:00.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad913a0-05ae-11e7-96a9-c5968f521666, 'PARTKEY01', 'CLUSTER08', 'QCOXCPFMSDWHWAM', 864188858, [ 'dreams',
'suspense' ], {
    'badly' : 'or',
    'do' : 'the'
}, { 'discovered', 'show', 'text' }, 'constantly', '2001-03-23 19:16:29.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad93ab0-05ae-11e7-96a9-c5968f521666, 'PARTKEY08', 'CLUSTER05', 'BHPYVZASANKUTOY', -1884769757, [ 'in', 'will'
], {
    'discovered' : 'text',
    'dreams' : 'suspense',
    'show' : 'what'
}, { 'probably', 'to', 'while' }, 'constantly', '1963-12-02 10:18:32.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 8ad988d0-05ae-11e7-96a9-c5968f521666, 'PARTKEY01', 'CLUSTER06', 'EWHWNLLKLKMCWPQ', 720568374, [ 'probably',
'headphones', 'caves' ], {
    'hold' : 'have'
}, { 'asked', 'do' }, 'constantly', '1949-12-26 03:16:59.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 949c5050-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY10', 'CLUSTER08', 'EBYWYYPGQORIGHF', -1877830097, [ 'of', 'web'
], {
    'it''s' : 'secretly',
    'library' : 'sacrifice',
    'out' : 'ghost'
}, { }, 'television', '1998-09-18 17:13:18.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 949e7330-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY07', 'CLUSTER06', 'XGXZZDURIFEUBRH', 172795051, [ 'this',
'island', 'ill', 'who' ], {
    'of' : 'sidekick',
    'so' : 'hot'
}, { 'found' }, 'constantly', '1998-05-02 19:39:59.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 949e7331-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY02', 'CLUSTER07', 'ZQAPHGVJJFHZCPO', 1392257099, [ 'have',
'want' ], {
    'boon' : 'hot',
    'or' : 'world',
    'so' : 'from'
}, { }, 'constantly', '2009-07-12 19:26:34.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 949e9a40-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY03', 'CLUSTER08', 'COEZHXPQXQDGQGQ', 791187325, [ 'hid',
'library' ], {
    'have' : 'hat',
    'probably' : 'to',
    'so' : 'this',
    'warp' : 'throw'
}, { 'sill' }, 'television', '1919-09-19 08:47:38.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 949f5d90-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY05', 'CLUSTER04', 'TRFTRGQXNOZOCHT', 1938342484, [ 'had', 'is',
'wrong' ], {
    'about' : 'to',
    'code' : 'room',
    'sidekick' : 'man',
    'smoke' : 'television'
}, { 'the', 'what', 'with', 'write' }, 'discovered', '2004-09-11 04:46:37.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a020e0-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY07', 'CLUSTER10', 'VCYQCODILPWBNLO', 1608297259, [ ], { }, {
'came', 'died', 'is', 'suspense', 'what' }, 'discovered', '1916-06-27 11:17:03.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a020e1-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY02', 'CLUSTER10', 'TAXFFVNZAHNYNHN', 28684069, [ 'wrong',
'wicket', 'do', 'the' ], {
    'was' : 'television'
}, { 'badly', 'dreams', 'or', 'suspense' }, 'discovered', '1928-11-07 17:32:46.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a047f0-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY07', 'CLUSTER10', 'BUFISFHEEFYBLDY', -1949989774, [ 'end', 'it'
], {
    'ghost' : 'or',
    'island' : 'to',
    'ship' : 'books'
}, { 'badly' }, 'television', '1949-01-22 09:27:36.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a06f00-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY10', 'CLUSTER06', 'WPYWWBQKVDYJUQZ', -197114244, [ 'ship' ], {
    'on' : 'on'
}, { 'it''s', 'of', 'secretly' }, 'constantly', '1982-01-29 05:08:24.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a09610-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY09', 'CLUSTER08', 'UVPVJYADOAPVSZD', -894289379, [ 'died',
'end', 'what', 'dad' ], {
    'always' : 'of',
    'have' : 'monster'
}, { 'about', 'ship' }, 'television', '1903-10-11 16:25:42.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a0e430-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY06', 'CLUSTER01', 'GOCCEMREEQDLOWW', -740368014, [ 'sits', 'my',
'handled' ], {
    'list' : 'monster',
    'shepherd' : 'we',
    'to' : 'captain'
}, { 'generated', 'have', 'waited' }, 'constantly', '1952-11-30 22:27:10.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a10b40-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY08', 'CLUSTER06', 'MWKYDGPZDWRXWAP', 1136102078, [ 'much', 'do',
'something', 'banging', 'to', 'where' ], {
    'is' : 'to',
    'of' : 'dreams'
}, { 'in', 'is', 'on' }, 'discovered', '1986-05-26 21:01:41.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a10b41-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY03', 'CLUSTER10', 'XNKYVSROBPPMOLF', 252362578, [ 'I' ], {
    'it' : 'badly',
    'red' : 'through',
    'ship' : 'monster',
    'to' : 'end'
}, { }, 'constantly', '1908-07-07 14:45:05.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a13250-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY07', 'CLUSTER10', 'NHXEZXNWZNYQJJO', 88566041, [ 'is',
'suspense', 'died', 'came' ], {
    'as' : 'what'
}, { 'died' }, 'constantly', '2003-09-06 00:33:59.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a15960-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY02', 'CLUSTER09', 'EGDXVTEWOSRMPEG', -970265868, [ 'text',
'good', 'dreams', 'probably' ], {
    'I' : 'until',
    'a' : 'asked',
    'library' : 'until',
    'numbers' : 'books',
    'ship' : 'sidekick',
    'speed' : 'discovered'
}, { }, 'constantly', '1982-10-12 11:00:46.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a18070-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY04', 'CLUSTER04', 'YLJMXIXTDOIIRRK', 1362858852, [ 'took' ], {
    'books' : 'demanded'
}, { 'to' }, 'constantly', '1982-11-13 18:48:26.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a243c0-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY03', 'CLUSTER05', 'GVAXCSJLLTVGRGQ', -1494888148, [ 'sacrifice',
'so' ], {
    'days' : 'of',
    'do' : 'shepherd',
    'good' : 'want',
    'of' : 'asked',
    'text' : 'crash',
    'web' : 'of'
}, { 'hot', 'of' }, 'television', '1928-07-15 07:22:24.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a26ad0-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY08', 'CLUSTER01', 'TXYQVSIPTZLMEXV', -920300722, [ 'sidekick',
'on' ], {
    'ends' : 'was'
}, { 'came', 'leader', 'this', 'write' }, 'headphones', '1937-06-09 16:38:48.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a291e0-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY02', 'CLUSTER02', 'MMVNPWBHPYVZASA', 296125804, [ 'with' ], {
    'the' : 'what'
}, { 'write' }, 'television', '2011-09-01 18:29:26.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 94a2b8f0-05ae-11e7-b6ce-17dc54f1e8e5, 'PARTKEY08', 'CLUSTER09', 'QFIUEOMIJKYHEWH', 47541117, [ 'plane',
'ball', 'ghost' ], {
    'in' : 'trying',
    'they' : 'in',
    'trying' : 'sill'
}, { 'throw', 'warp' }, 'constantly', '1973-03-22 07:00:00.000+0000' );

SELECT COUNT(*) FROM primary_key_example;

SELECT * FROM primary_key_example WHERE id = 6996d330-05ae-11e7-af61-0f3ca22f5ce0 AND pk2 = 'PARTKEY10';

SELECT * FROM primary_key_example WHERE id = 6996d330-05ae-11e7-af61-0f3ca22f5ce0;

SELECT * FROM primary_key_example WHERE id = 6996d330-05ae-11e7-af61-0f3ca22f5ce0 ALLOW FILTERING;

SELECT * FROM primary_key_example WHERE pk2 = 'PARTKEY10' ALLOW FILTERING;

SELECT TOKEN(id,pk2), id, pk2 FROM primary_key_example WHERE id = 6996d330-05ae-11e7-af61-0f3ca22f5ce0 AND pk2 = 'PARTKEY10';

SELECT * FROM primary_key_example WHERE TOKEN(id,pk2) = 5131411872637886664;

SELECT * FROM primary_key_example WHERE TOKEN(pk2,id) = 5131411872637886664;

SELECT TOKEN(pk2,id), id, pk2 FROM primary_key_example WHERE id = 6996d330-05ae-11e7-af61-0f3ca22f5ce0 AND pk2 = 'PARTKEY10';

SELECT * FROM primary_key_example WHERE pk2 = 'PARTKEY10' AND id = 6996d330-05ae-11e7-af61-0f3ca22f5ce0;

SELECT * FROM primary_key_example  
    WHERE id IN (6996d330-05ae-11e7-af61-0f3ca22f5ce0,  781a7e20-05ae-11e7-ba0f-21dd452c7333, 699db100-05ae-11e7-af61-0f3ca22f5ce0) 
    AND pk2 IN ('PARTKEY10','PARTKEY03','PARTKEY09');
    
INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER08', 'EBYWYYPGQORIGHF', -472746616, [ 'shepherd',
'we', 'list' ], {
    'have' : 'came',
    'or maybe' : 'room',
    'to' : 'captain'
}, { 'monster' }, 'television', '1982-09-09 04:50:23.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER08', 'NAHZXGXZZDURIFE', -2076878841, [ 'suspense'
], {
    'or' : 'dreams',
    'the' : 'badly',
    'wicket' : 'do'
}, { 'discovered' }, 'television', '1913-10-30 20:55:52.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER08', 'BQZQAPHGVJJFHZC', 53438720, [ 'days',
'enter', 'it' ], {
    'monster' : 'where'
}, { 'about', 'asked' }, 'discovered', '2013-03-17 22:22:07.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER10', 'PQXQDGQGQRYYSGQ', 1262558601, [ 'of', 'died',
'end' ], {
    'monster' : 'always',
    'numbers' : 'have',
    'what' : 'assert'
}, { 'what' }, 'constantly', '1988-06-25 01:05:44.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER10', 'RKSDTRFTRGQXNOZ', 1807743463, [ 'warp' ], {
    'good' : 'good',
    'magnetic' : 'with',
    'much' : 'waited',
    'trees' : 'end'
}, { 'build' }, 'constantly', '1944-04-24 04:18:44.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER10', 'AFVCYQCODILPWBN', 417068235, [ 'so', 'this',
'probably', 'to' ], {
    'have' : 'hat',
    'warp' : 'throw'
}, { 'hid' }, 'television', '1919-09-19 08:47:46.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER10', 'TAXFFVNZAHNYNHN', -2113076627, [ 'plane' ], {
    'in' : 'library',
    'secretly' : 'lock',
    'we' : 'white'
}, { 'constantly' }, 'discovered', '1982-04-07 15:00:48.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER10', 'FWJEIELPEYXHBUF', 275755746, [ 'assert',
'ball', 'of', 'throw', 'came' ], {
    'automatically' : 'what',
    'of' : 'to',
    'something' : 'room',
    'what' : 'pique'
}, { 'about', 'through', 'to' }, 'constantly', '1974-03-11 07:11:13.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER03', 'WBQKVDYJUQZULXD', -2013075260, [ ], {
    'find' : 'light',
    'good' : 'shepherd',
    'numbers' : 'found',
    'what' : 'hold'
}, { 'have', 'headphones', 'probably' }, 'constantly', '1990-07-12 10:11:15.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER03', 'UVPVJYADOAPVSZD', -1747316462, [ 'sacrifice'
], {
    'days' : 'of',
    'do' : 'shepherd',
    'good' : 'want',
    'of' : 'asked',
    'text' : 'crash',
    'web' : 'of'
}, { 'hot', 'of', 'so' }, 'television', '1928-07-15 07:22:41.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER03', 'GOCCEMREEQDLOWW', -396628951, [ 'pull', 'to',
'this', 'something', 'they' ], {
    'books' : 'to',
    'end' : 'ball',
    'is' : 'on'
}, { 'as', 'dad' }, 'television', '2011-09-16 22:01:27.000+0000' );

INSERT INTO primary_key_example ( id, pk2, cc1, cc2, rand_int, rand_list, rand_map, rand_set, rand_text, rand_time )
VALUES ( 699db100-05ae-11e7-af61-0f3ca22f5ce0, 'PARTKEY09', 'CLUSTER03', 'WAPGXWNKZEQGMOP', -646332032, [ 'room' ], {
    'in' : 'hatch'
}, { 'came' }, 'constantly', '1986-10-08 05:33:06.000+0000' );

SELECT * FROM primary_key_example WHERE id = 699db100-05ae-11e7-af61-0f3ca22f5ce0 AND pk2 = 'PARTKEY09';

SELECT * FROM primary_key_example WHERE id = 699db100-05ae-11e7-af61-0f3ca22f5ce0 AND pk2 = 'PARTKEY09' AND cc1 = 'CLUSTER10';

SELECT * FROM primary_key_example WHERE id = 699db100-05ae-11e7-af61-0f3ca22f5ce0 AND pk2 = 'PARTKEY09' AND cc1 > 'CLUSTER07';

SELECT * FROM primary_key_example WHERE id = 699db100-05ae-11e7-af61-0f3ca22f5ce0 AND pk2 = 'PARTKEY09' AND cc1 > 'CLUSTER04' AND cc1 <= 'CLUSTER08';

SELECT * FROM primary_key_example WHERE id = 699db100-05ae-11e7-af61-0f3ca22f5ce0 AND pk2 = 'PARTKEY09' AND cc1 = 'CLUSTER08' AND cc2 > 'C';

SELECT * FROM primary_key_example WHERE id = 699db100-05ae-11e7-af61-0f3ca22f5ce0 AND pk2 = 'PARTKEY09' AND cc1 > 'CLUSTER04' AND cc1 <= 'CLUSTER08' AND cc2 > 'M';

SELECT * FROM primary_key_example WHERE id = 699db100-05ae-11e7-af61-0f3ca22f5ce0 AND pk2 = 'PARTKEY09' AND cc1 > 'CLUSTER05' AND cc2 = 'C';

    
