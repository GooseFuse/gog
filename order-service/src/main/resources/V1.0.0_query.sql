INSERT INTO addresses(city, country, street, zipcode) VALUES
    ('test city1', 'test country1', 'test street1', 'test zipcode1'),
    ('test city2', 'test country2', 'test street2', 'test zipcode2'),
    ('test city3', 'test country3', 'test street3', 'test zipcode3');
INSERT INTO order_items(gameid) VALUES
    (1), (2), (3);
INSERT INTO payments(method, status) VALUES
    ('wire1', 'ACCEPTED'),
    ('wire2', 'ACCEPTED'),
    ('wire3', 'ACCEPTED');
