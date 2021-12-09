import { useLocation } from "react-router-dom";

const Page404 = () => {
  let location = useLocation();
  return (
    <div>
      <h1
        style={{
          width: "100%",
          textAlign: "center",
          margin: "auto",
          top: "30%",
          position: "absolute",
        }}
      >
        404: Trang không tồn tại <code>{location.pathname}</code>
      </h1>
    </div>
  );
};
export default Page404;
