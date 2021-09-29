import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory, {
  PaginationProvider,
  PaginationListStandalone,
} from "react-bootstrap-table2-paginator";

const TableWithPagination = ({ data, columns, onTableChange }) => {
  return (
    <div>
      <PaginationProvider
        pagination={paginationFactory({
          custom: true,
          page: data.number + 1,
          sizePerPage: data.size,
          totalSize: data.totalElements,
          //   paginationSize: 3,
        })}
      >
        {({ paginationProps, paginationTableProps }) => (
          <div>
            <BootstrapTable
              remote
              keyField="id"
              data={data.content}
              columns={columns}
              onTableChange={onTableChange}
              {...paginationTableProps}
            />
            <PaginationListStandalone {...paginationProps} />
          </div>
        )}
      </PaginationProvider>
    </div>
  );
};

export default TableWithPagination;
