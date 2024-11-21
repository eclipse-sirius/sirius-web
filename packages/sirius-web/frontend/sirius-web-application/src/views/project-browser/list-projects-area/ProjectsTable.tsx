/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import ArrowBack from '@mui/icons-material/ArrowBack';
import ArrowForward from '@mui/icons-material/ArrowForward';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import MenuItem from '@mui/material/MenuItem';
import Paper from '@mui/material/Paper';
import Select from '@mui/material/Select';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';

import FormControl from '@mui/material/FormControl';
import { ProjectsTableProps } from './ProjectsTable.types';
import { projectsTableRowExtensionPoint } from './ProjectsTableExtensionPoints';

export const ProjectsTable = ({
  projects,
  hasPrev,
  hasNext,
  onPrev,
  onNext,
  pageSize,
  onChange,
  onPageSizeChange,
}: ProjectsTableProps) => {
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
      <Box display="flex" justifyContent="flex-end" alignItems="center" width="100%">
        <Box display="flex" alignItems="center">
          <Typography variant="body2" mr={1}>
            Projects per page:
          </Typography>
          <FormControl size="small">
            <Select
              value={pageSize}
              onChange={(e) => onPageSizeChange(Number(e.target.value))}
              inputProps={{ 'aria-label': 'Projects per page' }}>
              {[5, 10, 20, 50].map((option) => (
                <MenuItem key={option} value={option}>
                  {option}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>
        <Box display="flex" alignItems="center">
          <IconButton onClick={onPrev} disabled={!hasPrev}>
            <ArrowBack />
          </IconButton>
          <IconButton onClick={onNext} disabled={!hasNext}>
            <ArrowForward />
          </IconButton>
        </Box>
      </Box>
    </Paper>
  );
};
