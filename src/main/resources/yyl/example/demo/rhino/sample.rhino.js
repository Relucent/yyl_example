map.put('k1', 'value1');
map.put('k2', 'value2');
map.put('k3', 'value3');

_.log(map);

var object = {};
_.log(object);

var array = [];
_.log(array);

var string = '{"name":"Nashorn"}';
_.log(string);

_.log(JSON);

var data = JSON.parse(string);
_.log(data);

var str123 = '0123'.replace(/^\w/, '');
_.log(str123);

'ok';