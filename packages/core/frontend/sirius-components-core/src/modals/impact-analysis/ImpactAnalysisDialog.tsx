/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import FiberManualRecordIcon from '@mui/icons-material/FiberManualRecord';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import CircularProgress from '@mui/material/CircularProgress';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { SxProps, Theme } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import { makeStyles } from 'tss-react/mui';
import { ImpactAnalysisDialogProps, ReportViewerProps } from './ImpactAnalysisDialog.types';

const useImpactAnalysisDialogStyles = makeStyles()((theme: Theme) => ({
  title: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    paddingTop: theme.spacing(2),
    paddingLeft: theme.spacing(2),
  },
  titleIcon: {
    color: theme.palette.primary.main,
    fontSize: theme.spacing(4),
  },
  confirmButton: {
    backgroundColor: theme.palette.primary.main,
    color: 'white',
    '&:hover': {
      backgroundColor: theme.palette.primary.dark,
    },
  },
}));

export const ImpactAnalysisDialog = ({
  open,
  label,
  impactAnalysisReport,
  loading,
  onConfirm,
  onCancel,
}: ImpactAnalysisDialogProps) => {
  const { classes } = useImpactAnalysisDialogStyles();

  return (
    <Dialog
      open={open}
      onClose={onCancel}
      aria-labelledby="impact-analysis-dialog"
      data-testid="impact-analysis-dialog">
      <div className={classes.title}>
        <ErrorOutlineIcon className={classes.titleIcon} />
        <DialogTitle data-testid="impact-analysis-dialog-title">{`You are about to ${label}`}</DialogTitle>
      </div>
      <DialogContent>
        {impactAnalysisReport != null ? (
          <ReportViewer
            nbElementCreated={impactAnalysisReport.nbElementCreated}
            nbElementDeleted={impactAnalysisReport.nbElementDeleted}
            nbElementModified={impactAnalysisReport.nbElementModified}
            additionalReports={impactAnalysisReport.additionalReports}
            label={label}
          />
        ) : (
          <CircularProgress />
        )}
      </DialogContent>
      <DialogActions>
        <Button
          data-testid="impact-analysis-dialog-button-ok"
          className={classes.confirmButton}
          onClick={onConfirm}
          disabled={loading}
          variant="contained"
          autoFocus>
          Execute
        </Button>
      </DialogActions>
    </Dialog>
  );
};

const ReportViewer = ({
  nbElementCreated,
  nbElementDeleted,
  nbElementModified,
  additionalReports,
  label,
}: ReportViewerProps) => {
  const listStyle: SxProps<Theme> = {
    padding: '0',
  };
  const listItemStyle: SxProps<Theme> = (theme) => ({
    gap: theme.spacing(0.5),
    padding: theme.spacing(0, 1),
  });
  const listItemIconStyle: SxProps<Theme> = {
    minWidth: 'auto',
  };
  const iconStyle: SxProps<Theme> = (theme) => ({
    fontSize: theme.spacing(0.8),
  });
  return (
    <Box>
      <Typography>{`The ${label} tool will have the following effects on your model:`}</Typography>
      <List dense sx={listStyle}>
        {nbElementCreated > 0 && (
          <ListItem key={'report.nbElementCreated'} sx={listItemStyle}>
            <ListItemIcon sx={listItemIconStyle}>
              <FiberManualRecordIcon sx={iconStyle} />
            </ListItemIcon>
            <ListItemText
              primary={`Elements added: ${nbElementCreated}`}
              data-testid="impact-analysis-report-nbElementCreated"
            />
          </ListItem>
        )}
        {nbElementDeleted > 0 && (
          <ListItem key={'report.nbElementDeleted'} sx={listItemStyle}>
            <ListItemIcon sx={listItemIconStyle}>
              <FiberManualRecordIcon sx={iconStyle} />
            </ListItemIcon>
            <ListItemText
              primary={`Elements deleted: ${nbElementDeleted}`}
              data-testid="impact-analysis-report-nbElementDeleted"
            />
          </ListItem>
        )}
        {nbElementModified > 0 && (
          <ListItem key={'report.nbElementModified'} sx={listItemStyle}>
            <ListItemIcon sx={listItemIconStyle}>
              <FiberManualRecordIcon sx={iconStyle} />
            </ListItemIcon>
            <ListItemText
              primary={`Elements modified: ${nbElementModified}`}
              data-testid="impact-analysis-report-nbElementModified"
            />
          </ListItem>
        )}
        {additionalReports?.length > 0 &&
          additionalReports.map((additionalReport, index) => {
            return (
              <ListItem key={`report.additionalReportData.${index}`} sx={listItemStyle}>
                <ListItemIcon sx={listItemIconStyle}>
                  <FiberManualRecordIcon sx={iconStyle} />
                </ListItemIcon>
                <ListItemText primary={additionalReport} data-testid={`additionalReportData_${index}`} />
              </ListItem>
            );
          })}
      </List>
      <Typography>Are you sure you want to proceed?</Typography>
    </Box>
  );
};
