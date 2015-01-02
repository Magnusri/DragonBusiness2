select * from player where company_company_id = (select company_id from company where company_name = 'OMGco!');

select * from player;

select * from company;

DELETE FROM company WHERE company_id=6;

UPDATE player SET company_company_id=4 WHERE player_uuid='8dcac680-10d3-4fa0-b4c8-4a121148a3ed';

select * from player where player_uuid='8dcac680-10d3-4fa0-b4c8-4a121148a3ed';

INSERT INTO player (player_uuid, player_name, player_rank, player_earned) VALUES ('gwgwreg', 'kalle', 'none', 0.0);