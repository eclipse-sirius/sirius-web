/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { useSelection } from '@eclipse-sirius/sirius-components-core';
import CodeIcon from '@mui/icons-material/Code';
import FormatBoldIcon from '@mui/icons-material/FormatBold';
import FormatItalicIcon from '@mui/icons-material/FormatItalic';
import FormatListBulletedIcon from '@mui/icons-material/FormatListBulleted';
import FormatListNumberedIcon from '@mui/icons-material/FormatListNumbered';
import HelpOutlineOutlined from '@mui/icons-material/HelpOutlineOutlined';
import StrikethroughSIcon from '@mui/icons-material/StrikethroughS';
import SubjectIcon from '@mui/icons-material/Subject';
import TitleIcon from '@mui/icons-material/Title';
import Divider from '@mui/material/Divider';
import Paper from '@mui/material/Paper';
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import Typography from '@mui/material/Typography';
import { useEffect, useRef, useState } from 'react';
import { makeStyles, withStyles } from 'tss-react/mui';
import { RichTextWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles()((theme) => ({
  selected: {
    color: theme.palette.primary.main,
  },
  editorContainer: {
    marginTop: theme.spacing(2),
    borderRadius: '2px',
    borderColor: theme.palette.divider,
    borderWidth: '1px',
    borderStyle: 'solid',
    color: theme.palette.text.primary,
    position: 'relative',
    fontWeight: Number(theme.typography.fontWeightRegular),
    textAlign: 'left',
  },
  paper: {
    display: 'flex',
    flexDirection: 'row',
    borderBottom: `1px solid ${theme.palette.divider}`,
    flexWrap: 'wrap',
  },
  divider: {
    margin: theme.spacing(1, 0.5),
  },
  button: {
    color: theme.palette.primary.light,
    '&.Mui-selected': {
      color: theme.palette.primary.main,
    },
  },
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
}));

const StyledToggleButtonGroup = withStyles(ToggleButtonGroup, (theme) => ({
  grouped: {
    margin: theme.spacing(0.5),
    border: 'none',
    '&:not(:first-child)': {
      borderRadius: theme.shape.borderRadius,
    },
    '&:first-child': {
      borderRadius: theme.shape.borderRadius,
    },
  },
}));

export const RichTextWidget = ({ widget }: RichTextWidgetProps) => {
  const { classes } = useStyles();
  const [selected, setSelected] = useState<boolean>(false);
  const { selection } = useSelection();
  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setSelected(true);
    } else {
      setSelected(false);
    }
  }, [selection, widget]);

  return (
    <div>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="inherit" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
      <div onFocus={() => setSelected(true)} onBlur={() => setSelected(false)} ref={ref} tabIndex={0}>
        <Paper elevation={0} className={classes.paper}>
          <StyledToggleButtonGroup size="small">
            <ToggleButton
              classes={{ root: classes.button }}
              selected
              disabled={false}
              value={'paragraph'}
              key={'paragraph'}>
              <SubjectIcon fontSize="small" />
            </ToggleButton>
            <ToggleButton
              classes={{ root: classes.button }}
              selected={false}
              disabled={false}
              value={'header1'}
              key={'header1'}>
              <TitleIcon fontSize="small" />
            </ToggleButton>
            <ToggleButton
              classes={{ root: classes.button }}
              selected={false}
              disabled={false}
              value={'bullet-list'}
              key={'bullet-list'}>
              <FormatListBulletedIcon fontSize="small" />
            </ToggleButton>
            <ToggleButton
              classes={{ root: classes.button }}
              selected={false}
              disabled={false}
              value={'number-list'}
              key={'number-list'}>
              <FormatListNumberedIcon fontSize="small" />
            </ToggleButton>
          </StyledToggleButtonGroup>
          <Divider flexItem orientation="vertical" className={classes.divider} />
          <StyledToggleButtonGroup size="small">
            <ToggleButton classes={{ root: classes.button }} disabled={false} value={'bold'} key={'bold'}>
              <FormatBoldIcon fontSize="small" />
            </ToggleButton>
            <ToggleButton classes={{ root: classes.button }} value={'italic'} key={'italic'}>
              <FormatItalicIcon fontSize="small" />
            </ToggleButton>
            <ToggleButton classes={{ root: classes.button }} disabled={false} value={'code'} key={'code'}>
              <CodeIcon fontSize="small" />
            </ToggleButton>
            <ToggleButton
              classes={{ root: classes.button }}
              disabled={false}
              value={'strikethrough'}
              key={'strikethrough'}>
              <StrikethroughSIcon fontSize="small" />
            </ToggleButton>
          </StyledToggleButtonGroup>
        </Paper>
        <div className={classes.editorContainer}>
          <Typography variant="h4">Rich text document</Typography>
          <Typography variant="body1" gutterBottom>
            Your <b>rich text</b>.
          </Typography>
        </div>
      </div>
    </div>
  );
};
