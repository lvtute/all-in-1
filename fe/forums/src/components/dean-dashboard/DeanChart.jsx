import { useState, useEffect } from "react";
import { Row } from "react-bootstrap";
import Chart from "react-google-charts";
import chartService from "../../services/chart.service";

const DeanChart = () => {
  const [topicQuestionPieChartData, setTopicQuestionPieChartData] = useState(
    []
  );

  useEffect(() => {
    chartService
      .getTopicQuestionPieChartData()
      .then((res) => {
        setTopicQuestionPieChartData(res.data);
      })
      .catch((err) => {
        console.log(err.response.data);
      });
  }, []);

  let firstChartData = [["Chủ đề", "Số lượng"]];
  topicQuestionPieChartData.forEach((e) => {
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
            title: "Phân bổ câu hỏi theo chủ đề trong khoa",
            // Just add this option
            is3D: true,
          }}
          rootProps={{ "data-testid": "2" }}
        />
      </Row>
    </>
  );
};
export default DeanChart;
