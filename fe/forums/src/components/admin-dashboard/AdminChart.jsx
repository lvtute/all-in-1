import { useEffect, useState } from "react";
import Chart from "react-google-charts";
import chartService from "../../services/chart.service";

const AdminChart = () => {
  const [facultyUserPieChartData, setFacultyUserPieChartData] = useState([]);

  useEffect(() => {
    chartService
      .getFacultyUserPieChartData()
      .then((res) => {
        setFacultyUserPieChartData(res.data);
        // console.log(res.data);
      })
      .catch((err) => {
        console.log(err.response.data);
      });
  }, []);
  let firstChartData = [["Khoa", "Số lượng"]];
  facultyUserPieChartData.forEach((element) => {
    firstChartData.push([element.facultyName, element.amountOfUsers]);
  });

  return (
    <>
      <h4>Thống kê và biểu đồ</h4>
      <div style={{ display: "flex", maxWidth: "100%" }}>
        <Chart
          width={600}
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
        <Chart
          width={600}
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
      </div>
    </>
  );
};
export default AdminChart;
