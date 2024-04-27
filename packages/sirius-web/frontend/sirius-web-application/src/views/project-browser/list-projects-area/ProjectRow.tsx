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

import Link from '@mui/material/Link';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import { makeStyles } from 'tss-react/mui';
import { Link as RouterLink } from 'react-router-dom';
import { ProjectActionButton } from './ProjectActionButton';
import { ProjectRowProps } from './ProjectRow.types';

const useProjectsRowStyles = makeStyles()(() => ({
  projectLabel: {
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    maxWidth: '100ch',
  },
}));

export const ProjectRow = ({ project, onChange }: ProjectRowProps) => {
  const { classes } = useProjectsRowStyles();
  return (
    <TableRow key={project.id} hover>
      <TableCell>
        <Link
          component={RouterLink}
          to={`/projects/${project.id}/edit`}
          color="inherit"
          className={classes.projectLabel}>
          {project.name}
        </Link>
      </TableCell>
      <TableCell align="right">
        <ProjectActionButton project={project} onChange={onChange} />
      </TableCell>
    </TableRow>
  );
};
