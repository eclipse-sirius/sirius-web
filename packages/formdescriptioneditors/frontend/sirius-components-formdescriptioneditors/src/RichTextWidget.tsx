/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import Divider from '@material-ui/core/Divider';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import CodeIcon from '@material-ui/icons/Code';
import FormatBoldIcon from '@material-ui/icons/FormatBold';
import FormatItalicIcon from '@material-ui/icons/FormatItalic';
import FormatListBulletedIcon from '@material-ui/icons/FormatListBulleted';
import FormatListNumberedIcon from '@material-ui/icons/FormatListNumbered';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import StrikethroughSIcon from '@material-ui/icons/StrikethroughS';
import SubjectIcon from '@material-ui/icons/Subject';
import TitleIcon from '@material-ui/icons/Title';
import ToggleButton from '@material-ui/lab/ToggleButton';
import ToggleButtonGroup from '@material-ui/lab/ToggleButtonGroup';
import { useEffect, useRef, useState } from 'react';
import { RichTextWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles((theme) => ({
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

const StyledToggleButtonGroup = withStyles((theme) => ({
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
}))(ToggleButtonGroup);

export const RichTextWidget = ({ widget }: RichTextWidgetProps) => {
  const classes = useStyles();
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
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
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
