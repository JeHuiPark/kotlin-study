# Use

```shell
curl -w "\n" http://localhost:8080/tasks
echo "======================================================================"
curl -w "\n" -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"description": "write code"}' 
curl -w "\n" -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"description": "test"}'
echo "======================================================================"
curl -w "\n" http://localhost:8080/tasks
echo "======================================================================"
curl -w "\n" -X DELETE http://localhost:8080/tasks/1
curl -w "\n" -X DELETE http://localhost:8080/tasks/-1
echo "======================================================================"
curl -w "\n" http://localhost:8080/tasks
```