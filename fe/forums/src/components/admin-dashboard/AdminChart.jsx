import { useEffect, useState } from "react";
import { Row } from "react-bootstrap";
import Chart from "react-google-charts";
import chartService from "../../services/chart.service";

const AdminChart = () => {
  const [facultyUserPieChartData, setFacultyUserPieChartData] = useState([]);

  useEffect(() => {
    chartService
      .getFacultyUserPieChartData()
      .then((res) => {
        setFacultyUserPieChartData(res.data);
      })
      .catch((err) => {
        console.log(err.response.data);
      });
  }, []);

  let firstChartData = [["Khoa", "Số lượng"]];
  facultyUserPieChartData.forEach((e) => {
    firstChartData.push([e[Object.keys(e)[0]], e[Object.keys(e)[1]]]);
  });

  return (
    <>
      <h4>Thống kê và biểu đồ</h4>
      <Row>
        <Chart
          width={800}
          height={500}
          chartType="PieChart"
          loader={<div>Loading Chart</div>}
          data={firstChartData}
          options={{
            title: "Phân bổ thành viên trong các khoa",
            // Just add this option
            is3D: true,
          }}
          rootProps={{ "data-testid": "2" }}
        />
       
      </Row>
    </>
  );
};
export default AdminChart;
