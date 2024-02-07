/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import { useComponent } from '@eclipse-sirius/sirius-components-core';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import { ProjectsTableProps } from './ProjectsTable.types';
import { projectsTableRowExtensionPoint } from './ProjectsTableExtensionPoints';

export const ProjectsTable = ({ projects, page, limit, count, onChange, onPageChange }: ProjectsTableProps) => {
  const { Component: ProjectRow } = useComponent(projectsTableRowExtensionPoint);

  return (
    <Paper>
      <TableContainer>
        <Table>
          <colgroup>
            <col width="60%" />
            <col width="40%" />
          </colgroup>
          <TableHead>
            <TableRow>
              <TableCell variant="head">Name</TableCell>
              <TableCell variant="head"></TableCell>
            </TableRow>
          </TableHead>
          <TableBody data-testid="projects">
            {projects.map((project) => (
              <ProjectRow key={project.id} project={project} onChange={onChange} />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        component="div"
        rowsPerPageOptions={[]}
        rowsPerPage={limit}
        page={page}
        onPageChange={(_event, page) => onPageChange(page)}
        count={count}
      />
    </Paper>
  );
};
