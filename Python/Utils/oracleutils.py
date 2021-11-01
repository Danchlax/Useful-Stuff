from config import config
import cx_Oracle

def execute_select_query_oracle(query, parameter):
    connection = cx_Oracle.connect(config.oracle_username, config.oracle_password, config.oracle_url)
    cursor = connection.cursor()
    cursor.execute(query, [parameter])
    result = cursor.fetchall()
    print(result)
    return_param = [result[0][0], result[0][1], result[0][2].read()]
    print(return_param)
    connection.close()
    return return_param
